package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.services.BookService;
import com.bizzybees.bizzybooky.services.RentalService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/books") // see if slash is needed
public class BookController {
    private BookService bookService;
    private RentalService rentalService;

    public BookController(BookService bookService, RentalService rentalService ) {
        this.bookService = bookService;
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    public BookService getBookService() {
        return bookService;
    }


    @RequestMapping(path="/{isbn}",produces = "application/json")
    public BookDto getBookByIsbn(@PathVariable String isbn){
        return bookService.getBookByIsbn(isbn);
    }

    @GetMapping(path = "/{id}/{isbn}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookRental rentBook(@PathVariable String id,@PathVariable String isbn){
        return rentalService.rentBook(id,isbn);
    }
}
