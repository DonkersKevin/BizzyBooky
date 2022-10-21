package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.dto.BookDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    BookController bookController;

    @Test
    void getAllBooks() {
        //ARRANGE
        List<BookDto> expectedBookList = new ArrayList<>(List.of(
                new BookDto("1000-2000-3000", "Pirates", "Mister", "Crabs"),
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato"),
                new BookDto("3000-4000-5000", "Gardeners", "Miss", "Lettuce"),
                new BookDto("6000-7000-8000", "Programmes", "Boy", "Name")
        ));

        //ACT
        BookDto[] result = RestAssured
                .given()
                .baseUri("http://localhost:8080")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        //ASSES
        // List<BookDto> expectedList = bookController.getBookService().getBookMapper().bookToDto();
        assertThat(List.of(result)).isEqualTo(expectedBookList);

    }
}