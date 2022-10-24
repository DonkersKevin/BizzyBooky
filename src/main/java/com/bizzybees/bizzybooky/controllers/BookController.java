package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    //Check if logger class declaration needs to be explicit....
    private final Logger log = LoggerFactory.getLogger(getClass());
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("id/{id}")
    public BookDto getBookById(@PathVariable String id) {
        log.info("Looking for book with id: " + id);
        return bookService.getBookById(id);
    }

<<<<<<< HEAD
=======
    public BookService getBookService() {
        return bookService;
    }


    //Adapt the path naming according to conventions -> use query parameters
>>>>>>> 5b81447b7074ee6dd713dfe1789c40bde19d44ac
    @RequestMapping(path = "isbn/{isbn}", produces = "application/json")
    public BookDto getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

<<<<<<< HEAD
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"title"})
    public List<BookDto> getAllBooksWithPartialTitle(@RequestParam String title){
        return bookService.getBooksByTitle(title);
    }

=======
    @GetMapping(path = "/{id}/{isbn}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookRental rentBook(@PathVariable String id,@PathVariable String isbn){
        return rentalService.rentBook(id,isbn);
    }
>>>>>>> 5b81447b7074ee6dd713dfe1789c40bde19d44ac
}
