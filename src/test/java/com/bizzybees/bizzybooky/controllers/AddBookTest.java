package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookDto;
import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookMapper;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.services.BookService;
import com.bizzybees.bizzybooky.services.util.BookValidator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddBookTest {
    @LocalServerPort
    private int port;
    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void givenNoIsbn_ThrowException() {
        BookDto bookDto = new BookDto("", "a", "a", "a", "a");
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.addBook(bookDto));
    }

    @Test
    void givenNoTitle_ThrowException() {
        BookDto bookDto = new BookDto("e", "", "a", "a", "a");
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.addBook(bookDto));
    }

    @Test
    void givenNoLastName_ThrowException() {
        BookDto bookDto = new BookDto("", "a", "a", "a", "a");
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.addBook(bookDto));
    }

    @Test
    void givenExistingIsbn_ThrowException() {
        BookDto bookDto = new BookDto("1", "a", "a", "a", "a");
        bookService.addBook(bookDto);
        BookDto bookDto1 = new BookDto("1", "a", "a", "a", "a");
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.addBook(bookDto1));
    }

    @Test
    void givenNewBookEntry_BookIsAddedToBookRepository() {


        String requestedBody = "{\"isbn\":\"1100-2000-3000\",\"title\":\"a\",\"authorFirstname\":\"g\",\"authorLastName\":\"nana\",\"summary\":\"g\"}";

        BookDto result = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth().preemptive().basic("3", "Squarepants")
                .body(requestedBody)
                .when()
                .accept(ContentType.JSON)
                .post("/books/add")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookDto.class);

    }

    @Test
    void whenNoAuthorization_Http403IsReturned() {


        String requestedBody = "{\"isbn\":\"1010-2000-3000\",\"title\":\"a\",\"authorFirstname\":\"g\",\"authorLastName\":\"nana\",\"summary\":\"g\"}";

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .auth().preemptive().basic("1", "Squarepants")
                .body(requestedBody)
                .when()
                .accept(ContentType.JSON)
                .post("/books/add")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());

    }
}
