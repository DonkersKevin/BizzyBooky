package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {
    private List <Book> bookList;

    public BookRepository(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getAllBooks() {
        return bookList;
    }
}
