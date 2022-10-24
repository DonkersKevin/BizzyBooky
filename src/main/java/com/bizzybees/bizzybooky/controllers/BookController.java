package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.services.BookService;
import com.bizzybees.bizzybooky.services.RentalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books") // see if slash is needed
public class BookController {

    //Check if class declaration needs to be explicit....
    private final Logger log = LoggerFactory.getLogger(getClass());
    private BookService bookService;
    private RentalService rentalService;

    public BookController(BookService bookService, RentalService rentalService) {
        this.bookService = bookService;
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
      //  return bookService.getAllBooksWithoutSummary();
       return bookService.getAllBooks();

    }


    public BookService getBookService() {
        return bookService;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto getBookByIsbn(@PathVariable String isbn) {
        log.info("Looking for book with ISBN: " + isbn);
        return bookService.getBookByIsbn(isbn);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"isbn"})
    public List<BookDto> getAllBooksByIsbnWildcardSearch(@RequestParam String isbn) {
        log.info("Looking for book with ISBN: " + isbn);
        return bookService.getAllBooksByIsbnWildcard(isbn);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"title"})
    public List<BookDto> getAllBooksWithPartialTitle(@RequestParam String title) {
        return bookService.getBooksByTitle(title);
    }


    //Fix uri
    @GetMapping(path = "/{id}/{isbn}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookRental rentBook(@PathVariable String id, @PathVariable String isbn) {
        return rentalService.rentBook(id, isbn);
    }
}
