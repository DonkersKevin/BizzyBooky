package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.exceptions.AuthorNotFoundException;
import com.bizzybees.bizzybooky.exceptions.IsbnNotFoundException;
import com.bizzybees.bizzybooky.exceptions.TitleNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.bizzybees.bizzybooky.repositories.util.WildcardToRegexConverter.wildcardToRegex;

@Repository
public class BookRepository {
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
                new Book("7000-8000-9000", "Error", "Err", "or", "Lorem Ipsum")));
    }

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Book getBookDetailsByIsbn(String isbn) {
        return bookList.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst().orElseThrow();
    }

    public List<Book> getBooksByTitleWithWildcards(String title) {
        List<Book> listToReturn = bookList.stream().filter(b -> b.getTitle().matches(wildcardToRegex(title))).toList();
        if (!listToReturn.isEmpty()) {
            return listToReturn;
        }
        throw new TitleNotFoundException();
    }

    //ToDo test isbnNotFoundException
    public List<Book> getBooksByIsbnWithWildcards(String isbn) {
        List<Book> listToReturn = bookList.stream().filter(b -> b.getIsbn().matches(wildcardToRegex(isbn))).toList();
        if (!listToReturn.isEmpty()) {
            return listToReturn;
        }
        throw new IsbnNotFoundException();
    }

    public List<Book> getBooksByAuthorWithWildcards(String author) {
        List<Book> combinedList = new ArrayList<>();
        String regex = wildcardToRegex(author);
        combinedList.addAll(getAllBooksByAuthorFirstNameRegex(regex));
        combinedList.addAll(getAllBooksByAuthorLastNameRegex(regex));
        if (!combinedList.isEmpty()) {
            return combinedList;
        }
        throw new AuthorNotFoundException();
    }

    private List<Book> getAllBooksByAuthorFirstNameRegex(String authorRegex){
        return bookList.stream().filter(b -> b.getAuthorFirstName().matches(authorRegex)).toList();
    }

    private List<Book> getAllBooksByAuthorLastNameRegex(String authorRegex){
        return bookList.stream().filter(c -> c.getAuthorLastName().matches(authorRegex)).toList();
    }

    public Book addBook(Book book) {
        bookList.add(book);
        return book;
    }

    public Book updateBook(Book book) {
        if (!isPresent(book)) {
            throw new IsbnNotFoundException();
        }
        int index = bookList.indexOf(getBookDetailsByIsbn(book.getIsbn()));
        bookList.set(index, book);
        return bookList.get(index);
    }

    public void deleteBook(Book book) {
        if (!isPresent(book)) {
            throw new IsbnNotFoundException();
        }
        bookList.remove(book);
        System.out.println(bookList);
    }

    public boolean isPresent(Book book) {
        return bookList.contains(book);
    }
}
