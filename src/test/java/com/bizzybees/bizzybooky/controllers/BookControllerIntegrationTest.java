package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    BookController bookController;

    List<BookDto> expectedBookList;

    @BeforeEach
    void init() {
        expectedBookList = new ArrayList<>(List.of(
                new BookDto("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new BookDto("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum"),
                new BookDto("6000-7000-8000", "Programmes", "Boy", "Name", "Lorem Ipsum")
        ));
    }


    @Test
    void WhenCallingBooks_GetFullBookListBack() {
        //ARRANGE

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
        assertThat(List.of(result)).isEqualTo(expectedBookList);
    }

    @Test
    void getBookByIsbn(){
        //Given
        BookDto bookDto = new BookDto("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum");
        //When
        BookDto result = RestAssured
                .given()
                .baseUri("http://localhost:8080")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/1000-2000-3000")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto.class);
        //Then
        assertThat(result).isEqualTo(bookDto);
    }

    @Test
    void GivenStringId_ReturnBookWIthGivenId() {
        //ARRANGE

        //ACT
        BookDto result = RestAssured
                .given()
                .baseUri("http://localhost:8080")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/3")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto.class);

        //ASSES
        //Code smell?
        assertEquals(result, expectedBookList.get(2));

    }

    @Test
    void GivenStringIdOutOfBounds_ThrowNoSuchElement() {
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {
                    BookDto[] result = RestAssured
                            //ARRANGE
                            .given()
                            .baseUri("http://localhost:8080")
                            .port(port)
                            //ACT
                            .when()
                            .accept(ContentType.JSON)
                            .get("/books/8")
                            .then()
                            .extract()
                            .as(BookDto[].class);

                });
    }
}