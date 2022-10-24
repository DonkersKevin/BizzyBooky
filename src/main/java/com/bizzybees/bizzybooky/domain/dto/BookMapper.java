package com.bizzybees.bizzybooky.domain.dto;

import com.bizzybees.bizzybooky.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    public List<BookDto> listToDtoList(List<Book> bookList) {
        return bookList.stream().map(book -> bookToDto(book)).collect(Collectors.toList());
    }


    public List<BookDto> listToDtoListNoSummary(List<Book> bookList) {
        return bookList.stream().map(book -> bookToDto_NoSummary(book)).collect(Collectors.toList());
    }

    public BookDto bookToDto(Book book) {
        return new BookDto(book.getIsbn(), book.getTitle(), book.getAuthorFirstName(), book.getAuthorLastName(), book.getSummary());
    }

    public BookDto bookToDto_NoSummary(Book book) {
        return new BookDto(book.getIsbn(), book.getTitle(), book.getAuthorFirstName(), book.getAuthorLastName());
    }

}
