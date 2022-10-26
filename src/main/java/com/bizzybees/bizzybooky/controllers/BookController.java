package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookDto;
import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookDtoWithoutSummary;
import com.bizzybees.bizzybooky.security.Feature;
import com.bizzybees.bizzybooky.security.SecurityService;
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
@RequestMapping("/books")
public class BookController {

    //Check if class declaration needs to be explicit....
    private final Logger log = LoggerFactory.getLogger(getClass());
    private BookService bookService;
    private RentalService rentalService;
    private SecurityService securityService;

    public BookController(BookService bookService, RentalService rentalService, SecurityService securityService) {
        this.bookService = bookService;
        this.rentalService = rentalService;
        this.securityService = securityService;
    }

    @GetMapping
    public List<BookDtoWithoutSummary> getAllBooks() {
        log.info("Fetching all books...");
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"isbn", "title", "author"})
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto addBook(@RequestHeader String authorization, @RequestBody BookDto bookDto) {
        log.info("Adding book with isbn: " + bookDto.getIsbn());
        securityService.validateAuthorization(authorization, Feature.ADD_BOOK);
        return bookService.addBook(bookDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateBook(@RequestHeader String authorization, @RequestBody BookDto bookDto) {
        log.info("Updating book with isbn: " + bookDto.getIsbn());
        securityService.validateAuthorization(authorization, Feature.CAN_UPDATE_BOOK);
        return bookService.updateBook(bookDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBook(@RequestHeader String authorization, @RequestBody BookDto bookDto) {
        log.info("Deleting book with isbn: " + bookDto.getIsbn());
        securityService.validateAuthorization(authorization, Feature.CAN_SOFT_DELETE_BOOK);
        bookService.deleteBook(bookDto);
    }

}
