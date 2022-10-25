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

    public BookRepository() {
        this.bookList = new ArrayList<>(List.of(
                new Book("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
                new Book("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new Book("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum"),
                new Book("6000-7000-8000", "Programmes", "Boy", "Name", "Lorem Ipsum")));
    }

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Book getBookDetailsByIsbn(String isbn) {
        return bookList.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst().orElseThrow();
    }

    /**
    public List<Book> getBooksByTitleAtLeastContaining(String title) {
        return bookList.stream().filter(b -> b.getTitle().contains(title)).toList();
    }
     */

    public List<Book> getBooksByTitleWithWildcards(String title) {
        List <Book> listToReturn = bookList.stream().filter(b -> b.getTitle().matches(wildcardToRegex(title))).toList();
        if (!listToReturn.isEmpty()){
            return listToReturn;
        }
        throw new TitleNotFoundException();
    }

    public List<Book> getBooksByIsbnWithWildcards(String isbn) {
        List <Book> listToReturn = bookList.stream().filter(b -> b.getIsbn().matches(wildcardToRegex(isbn))).toList();
        if (!listToReturn.isEmpty()){
            return listToReturn;
        }
        throw new IsbnNotFoundException();
    }

    public List<Book> getBooksByAuthorWithWildcards(String author) {
        List<Book> combinedList = new ArrayList<>();
        combinedList.addAll(bookList.stream().filter(b -> b.getAuthorFirstName().matches(wildcardToRegex(author))).toList());
        combinedList.addAll(bookList.stream().filter(b -> b.getAuthorLastName().matches(wildcardToRegex(author))).toList());
        if (!combinedList.isEmpty()){
            return combinedList;
        }
        throw new AuthorNotFoundException();
    }

    public Book addBook(Book book) {
        bookList.add(book);
        return book;
    }
}
