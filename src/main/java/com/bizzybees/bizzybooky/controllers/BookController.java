package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.domain.dto.BookDtoWithoutSummary;
import com.bizzybees.bizzybooky.services.BookService;
import com.bizzybees.bizzybooky.services.RentalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<BookDtoWithoutSummary> getAllBooks() {
    log.info("Fetching all books...");
        //  return bookService.getAllBooksWithoutSummary();
        return bookService.getAllBooks();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto getBookByIsbn(@PathVariable String isbn) {
        log.info("Looking for book with ISBN: " + isbn);
        return bookService.getBookByIsbn(isbn);
    }

    //TODO implement properly
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"isbn", "title","author"})
    public List<BookDto> getAllBooksCombinedSearch(@RequestParam(required = false) String isbn, @RequestParam(required = false) String title, @RequestParam(required = false) String author) {
        log.info("Looking for book with: " + isbn + " " + title + " " + author);
        return new ArrayList<>();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"isbn"})
    public List<BookDto> getAllBooksByIsbnWildcardSearch(@RequestParam String isbn) {
        log.info("Looking for book with isbn: " + isbn);
        return bookService.getAllBooksByIsbnSearch(isbn);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"title"})
    public List<BookDto> getAllBooksWithTitleWildcardSearch(@RequestParam String title) {
        log.info("Looking for book with title: " + title);
        return bookService.getBooksByTitleSearch(title);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"author"})
    public List<BookDto> getAllBooksWithAuthorWildcardSearch(@RequestParam String author) {
        log.info("Looking for book with author: " + author);
        return bookService.getBooksByAuthorSearch(author);
    }


    @GetMapping(path = "/{id}/{isbn}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookRental rentBook(@PathVariable String id, @PathVariable String isbn) {
        return rentalService.rentBook(id, isbn);

    }

    //Fix uri
    @GetMapping(path = "/{lendingId}/return", produces = MediaType.APPLICATION_JSON_VALUE)
    public String returnBook(@PathVariable String lendingId) {
        return rentalService.returnBook(lendingId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto addBook(@RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto);
    }
}
