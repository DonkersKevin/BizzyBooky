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


    public List<BookDto> getAllBooks() {
        return bookMapper.listToDtoList(bookRepository.getAllBooks());
    }
}
