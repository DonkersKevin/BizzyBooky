package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.domain.dto.BookMapper;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;
    private BookMapper bookMapper;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = new BookMapper();
    }

    public List<BookDto> getAllBooks() {
        return bookMapper.listToDtoList(bookRepository.getAllBooks());
    }

    /*
    public List<BookDto> getAllBooksWithoutSummary() {
        return bookMapper.listToDtoListNoSummary(bookRepository.getAllBooks());
    }

     */


    public BookMapper getBookMapper() {
        return bookMapper;
    }

    public BookDto getBookByIsbn(String isbn) {
        return bookMapper.bookToDto(bookRepository.getBookDetailsByIsbn(isbn));
    }

    public List<BookDto> getBooksByTitle(String title) {
        return bookMapper.listToDtoList(bookRepository.getBooksByTitleWithWildcards(title));
    }

    /**
     * Main method for testing purposes - to be removed later
     */

    public static void main(String[] args) {
        BookService bookService = new BookService(new BookRepository());
        System.out.println(bookService.getBookByIsbn("1000-2000-3000").toString());
    }

    public List<BookDto> getAllBooksByIsbnWildcard(String isbn) {
        return null;
        //TODO implement
    }

    /** Main method for testing purposes - to be removed later*/
}
