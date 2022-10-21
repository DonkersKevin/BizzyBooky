package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private List<Book> bookList;

    public BookRepository(List<Book> bookList) {
        this.bookList = new ArrayList<>(List.of(
                new Book("1","1000-2000-3000", "Pirates", "Mister", "Crabs","Lorem Ipsum"),
                new Book("2","2000-3000-4000", "Farmers", "Misses", "Potato","Lorem Ipsum"),
                new Book("3","3000-4000-5000", "Gardeners", "Miss", "Lettuce","Lorem Ipsum"),
                new Book("4","6000-7000-8000", "Programmes", "Boy", "Name","Lorem Ipsum")));
    }

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Book getBookDetailsByIsbn(String isbn) {
        return bookList.stream().filter(book -> book.getIsbn().equals(isbn)).findAny().orElseThrow();
    }

    public Book getBookById(String id) {
        return bookList.stream().filter(book -> book.getId().equals(id)).findFirst().orElseThrow();
    }
    
    
}
