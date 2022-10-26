package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookDto;
import com.bizzybees.bizzybooky.exceptions.AuthorNotFoundException;
import com.bizzybees.bizzybooky.exceptions.IsbnNotFoundException;
import com.bizzybees.bizzybooky.exceptions.TitleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.bizzybees.bizzybooky.repositories.util.WildcardToRegexConverter.wildcardToRegex;

@Repository
public class BookRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private List<Book> bookList;

    private List<Book> forbiddenBookList;

    public BookRepository() {
        this.bookList = new ArrayList<>(List.of(
                new Book("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
                new Book("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new Book("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum"),
                new Book("4000-5000-6000", "OverDueBook", "Dude", "Guy", "Lorem Ipsum"),
                new Book("6000-7000-8000", "Programmes", "Boy", "Name", "Lorem Ipsum")));

        this.forbiddenBookList = new ArrayList<>(List.of(
                new Book("4000-5000-6000", "OverDueBook2", "Dudeette", "Guyana", "Lorem Ipsum"),
                new Book("7000-8000-9000", "Error", "Err", "or", "Lorem Ipsum")));
    }

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Book getBookDetailsByIsbn(String isbn) {
        log.info("Getting book by isbn: " + isbn);
        return bookList.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst().orElseThrow();
    }


    private Book getHiddenBookDetailsByIsbn(String isbn) {
        log.info("Getting hidden book by isbn: " + isbn);
        return forbiddenBookList.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst().orElseThrow();
    }

    public List<Book> getBooksByTitleWithWildcards(String title) {
        log.info("Getting book by search: " + title);
        List<Book> listToReturn = bookList.stream().filter(b -> b.getTitle().matches(wildcardToRegex(title))).toList();
        if (!listToReturn.isEmpty()) {
            return listToReturn;
        }
        throw new TitleNotFoundException();
    }


    public List<Book> getBooksByIsbnWithWildcards(String isbn) {
        log.info("Getting book by search: " + isbn);
        List<Book> listToReturn = bookList.stream().filter(b -> b.getIsbn().matches(wildcardToRegex(isbn))).toList();
        if (!listToReturn.isEmpty()) {
            return listToReturn;
        }
        throw new IsbnNotFoundException();
    }

    public List<Book> getBooksByAuthorWithWildcards(String author) {
        log.info("Getting book by search: " + author);
        List<Book> combinedList = new ArrayList<>();
        String regex = wildcardToRegex(author);
        combinedList.addAll(getAllBooksByAuthorFirstNameRegex(regex));
        combinedList.addAll(getAllBooksByAuthorLastNameRegex(regex));
        if (!combinedList.isEmpty()) {
            return combinedList;
        }
        throw new AuthorNotFoundException();
    }

    private List<Book> getAllBooksByAuthorFirstNameRegex(String authorRegex) {
        return bookList.stream().filter(b -> b.getAuthorFirstName().matches(authorRegex)).toList();
    }

    private List<Book> getAllBooksByAuthorLastNameRegex(String authorRegex) {
        return bookList.stream().filter(c -> c.getAuthorLastName().matches(authorRegex)).toList();
    }

    public Book addBook(Book book) {
        bookList.add(book);
        return book;
    }

    public Book updateBook(Book book) {
        int index;

        if (!isPresentInBookList(book) && (!isPresentInForbiddenBookList(book))) {
            throw new IsbnNotFoundException();
        }

        if (!isPresentInForbiddenBookList(book)) {
            log.info("found in booklist");
            index = bookList.indexOf(getBookDetailsByIsbn(book.getIsbn()));
            bookList.set(index,book);
        } else {
            log.info("found in hidden booklist");
            index = forbiddenBookList.indexOf(getHiddenBookDetailsByIsbn(book.getIsbn()));
            forbiddenBookList.set(index,book);
        }
        return book;
    }

    public void deleteBook(Book book) {
        if (!isPresentInBookList(book)) {
            throw new IsbnNotFoundException();
        }
        log.info("Deleting book: " + book.getIsbn());
        bookList.remove(book);

        log.info("Adding book to storage: " + book.getIsbn());
        forbiddenBookList.add(book);
    }

    public Book unDeleteBook(Book book) {
        if (!isPresentInForbiddenBookList(book)) {
            throw new IsbnNotFoundException();
        }
        log.info("Undeleting book: " + book.getIsbn());
        bookList.add(book);

        log.info("Removing book to storage: " + book.getIsbn());
        forbiddenBookList.remove(book);
        return book;
    }

    private boolean isPresentInBookList(Book book) {
        log.info("Checking in booklist");
        return bookList.contains(book);
    }

    public boolean isPresentInForbiddenBookList(Book book) {
        log.info("Checking in hidden booklist");
        return forbiddenBookList.contains(book);
    }

    public List<Book> getAllForbiddenBooks() {
        return forbiddenBookList;
    }
}
