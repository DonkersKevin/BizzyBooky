package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @InjectMocks
    BookController bookController;
    @Mock
    BookService bookService;


    @Test
    void aListOfBooks_callTheBookServiceGetAllBooksMethod_bookControllerGetAllBooksMethodIsCalled() {
        //Given
        List<BookDto> expectedBookList = new ArrayList<>();
        Mockito.when(bookService.getAllBooks()).thenReturn(expectedBookList);
        //When
        List<BookDto> bookRepositoryToTest = bookController.getAllBooks();
        //Then
        Assertions.assertEquals(expectedBookList, bookRepositoryToTest);
    }
}