package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private List<Book> bookList;

    public BookRepository(List<Book> bookList) {
        this.bookList = new ArrayList<>(List.of(
                new Book("1000-2000-3000", "Pirates", "Mister", "Crabs"),
                new Book("2000-3000-4000", "Farmers", "Misses", "Potato"),
                new Book("3000-4000-5000", "Gardeners", "Miss", "Lettuce"),
                new Book("6000-7000-8000", "Programmes", "Boy", "Name")));
    }

    public List<Book> getAllBooks() {
        return bookList;
    }
}
