package com.bizzybees.bizzybooky.controllers;
import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
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
                .baseUri("http://localhost")
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
    void getBookByIsbn() {
        //Given
        BookDto bookDto = new BookDto("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum");
        //When
        BookDto result = RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/isbn/1000-2000-3000")
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
                .baseUri("http://localhost")
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
        RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/8")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("No book by that id..."));
    }

    @Test
    void titleSearch_HappyPath() {
        //ARRANGE
        List<BookDto> expectedBooks = List.of(
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new BookDto("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum")
        );

        //ACT
        BookDto[] result = RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books?title=ar")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        //ASSES
        assertThat(List.of(result)).isEqualTo(expectedBooks);
    }


    @Test
    void getRentalHappyPath(){
        //given
        BookRental bookRentalExpected = new BookRental("1", "1000-2000-3000");

        List<BookDto> expectedBookList = new ArrayList<>(List.of(
                new BookDto("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum")));

        //when
        LocalDate result = RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/1/1000-2000-3000/lent")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookRental.class).getDueDate();
        //Then
        assertThat(result).isEqualTo(LocalDate.of(2022,11,11));
        //then
        //Assertions.assertEquals(LocalDate.of(2022,11,11),rental.getDueDate());

        //TODO What do we give back when the list is empty?
    }
}