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

    public BookMapper getBookMapper() {
        return bookMapper;
    }

    public BookDto getBookByIsbn(String isbn) {
        return bookMapper.bookToDto(bookRepository.getBookDetailsByIsbn(isbn));
    }
}
