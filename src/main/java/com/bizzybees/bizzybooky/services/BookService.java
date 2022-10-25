package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.domain.dto.BookDtoWithoutSummary;
import com.bizzybees.bizzybooky.domain.dto.BookMapper;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import com.bizzybees.bizzybooky.services.util.BookValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private BookValidator bookValidator;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = new BookMapper();
    }

    public List<BookDtoWithoutSummary> getAllBooks() {
        return bookMapper.listToDtoListNoSummary(bookRepository.getAllBooks());
    }

    /*
    public List<BookDto> getAllBooksWithoutSummary() {
        return bookMapper.listToDtoListNoSummary(bookRepository.getAllBooks());
    }

     */

    public BookDto getBookByIsbn(String isbn) {
        return bookMapper.bookToDto(bookRepository.getBookDetailsByIsbn(isbn));
    }

    public List<BookDto> getBooksByTitleSearch(String title) {
        return bookMapper.listToDtoList(bookRepository.getBooksByTitleWithWildcards(title));
    }


    public List<BookDto> getAllBooksByIsbnSearch(String isbn) {
        return bookMapper.listToDtoList(bookRepository.getBooksByIsbnWithWildcards(isbn));
    }

    public List<BookDto> getBooksByAuthorSearch(String author) {
        return bookMapper.listToDtoList(bookRepository.getBooksByAuthorWithWildcards(author));
    }

    public BookDto addBook(BookDto bookDto) {
        bookValidator.checkRequiredFields(bookDto);
        Book newBook = bookRepository.addBook(bookMapper.dtoToBook(bookDto));
        return bookMapper.bookToDto(newBook);
    }


}

