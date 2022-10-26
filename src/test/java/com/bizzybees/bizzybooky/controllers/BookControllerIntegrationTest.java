package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookDto;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import com.bizzybees.bizzybooky.services.RentalService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    BookController bookController;
    @Autowired
    RentalService rentalService;
    @Autowired
    BookRepository bookRepository;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void GetBooks_HappyPath() {
        //ARRANGE
        ArrayList<BookDto> expectedBookListWithoutSummary = new ArrayList<>(List.of(
                new BookDto("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new BookDto("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum"),
                new BookDto("4000-5000-6000", "OverDueBook", "Dude", "Guy", "Lorem Ipsum"),
                new BookDto("6000-7000-8000", "Programmes", "Boy", "Name", "Lorem Ipsum")
        ));

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
        assertThat(List.of(result)).isEqualTo(expectedBookListWithoutSummary);
    }

    @Test
    void getBookByIsbn_happyPath() {
        //Given
        BookDto bookDto = new BookDto("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum");
        //When
        BookDto result = RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/1000-2000-3000")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto.class);
        //Then
        assertThat(result).isEqualTo(bookDto);
    }

    @Test
    void IsbnSearch_HappyPath() {
        //ARRANGE
        List<BookDto> expectedBooks = List.of(
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum")
        );

        //ACT
        BookDto[] result = RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books?isbn=*-3000-*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        //ASSES
        assertThat(List.of(result)).isEqualTo(expectedBooks);
    }

    @Test
    void GivenIsbnNotFound_ThrowNoSuchElement() {
        RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/9000-9000-9000")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("No book by that isbn..."));
    }

    @Test
    void IsbnWildCardSearch_WhenNotFoundThrowIsbnNotFound() {
        RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books?isbn=*11-1000-*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("No book by that isbn..."));
    }

    @Test
    void titleSearch_HappyPath() {
        //ARRANGE
        List<BookDto> expectedBookList = new ArrayList<>(List.of(
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"))
        );

        //ACT
        BookDto[] result = RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books?title=Farmers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        //ASSES
        assertThat(List.of(result)).isEqualTo(expectedBookList);
    }

    @Test
    void titleSearch_WildCardHappyPath() {
        //ARRANGE
        List<BookDto> expectedBookList = new ArrayList<>(List.of(
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"))
        );

        //ACT
        BookDto[] result = RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books?title=*rmer*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        //ASSES
        assertThat(List.of(result)).isEqualTo(expectedBookList);
    }

    @Test
    void titleSearch_WhenNotFound_ThrowException() {
        //ARRANGE
        List<BookDto> expectedBookList = new ArrayList<>(List.of(
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"))
        );

        //ACT
        RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books?title=farm")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("No book by that title..."));

        //ASSES
    }

    @Test
    void authorFirstNameSearch_HappyPath() {
        //ARRANGE
        List<BookDto> expectedBooks = List.of(
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum")
        );

        //ACT
        BookDto[] result = RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books?author=Misses")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        //ASSES
        assertThat(List.of(result)).isEqualTo(expectedBooks);
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void authorLastNameSearch_HappyPath() {
        //ARRANGE
        List<BookDto> expectedBooks = List.of(
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum")
        );

        //ACT
        BookDto[] result = RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books?author=Potato")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        //ASSES
        assertThat(List.of(result)).isEqualTo(expectedBooks);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void authorSearch_WildCardHappyPath() {
        //ARRANGE
        List<BookDto> expectedBooksList = List.of(
                new BookDto("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
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
                .get("/books?author=*t*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(BookDto[].class);

        //ASSES
        assertThat(List.of(result)).isEqualTo(expectedBooksList);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void authorSearch_WhenNotFound_ThrowException() {
        //ARRANGE
        //ACT
        RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books?author=*z*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("No book by that author..."));

        //ASSES
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void deleteBook_HappyPath() {
        //ARRANGE
        List<Book> expectedBooks = List.of(
                new Book("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
                new Book("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new Book("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum"),
                new Book("4000-5000-6000", "OverDueBook", "Dude", "Guy", "Lorem Ipsum")
        );

        String requestedBody = "{\"isbn\":\"6000-7000-8000\",\"title\":\"Programmes\",\"authorFirstname\":\"Boy\",\"authorLastName\":\"Name\",\"summary\":\"Lorem Ipsum\"}";

        //ACT
        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("3", "Squarepants")
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .body(requestedBody)
                .when()
                .delete("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());

        //ASSES
        assertThat(bookRepository.getAllBooks()).isEqualTo(expectedBooks);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void deleteBookByIsbn_HappyPath() {
        //ARRANGE
        List<Book> expectedBooks = List.of(
                new Book("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
                new Book("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new Book("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum"),
                new Book("4000-5000-6000", "OverDueBook", "Dude", "Guy", "Lorem Ipsum")
        );

        //ACT
        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("3", "Squarepants")
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .when()
                .delete("/books/6000-7000-8000")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());

        //ASSES
        assertThat(bookRepository.getAllBooks()).isEqualTo(expectedBooks);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void undeleteBook_HappyPath() {
        //ARRANGE
        List<Book> expectedBooks = List.of(
                new Book("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
                new Book("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new Book("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum"),
                new Book("4000-5000-6000", "OverDueBook", "Dude", "Guy", "Lorem Ipsum"),
                new Book("6000-7000-8000", "Programmes", "Boy", "Name", "Lorem Ipsum"),
                new Book("7000-8000-9000", "Error", "Err", "or", "Lorem Ipsum")
        );

        List<Book> expectedForbiddenBooks = List.of(
                new Book("4000-5000-6000", "OverDueBook2", "Dudeette", "Guyana", "Lorem Ipsum")
        );

        String requestedBody = "{\"isbn\":\"7000-8000-9000\",\"title\":\"Error\",\"authorFirstname\":\"Err\",\"authorLastName\":\"or\",\"summary\":\"Lorem Ipsum\"}";

        //ACT
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

        //ASSES
        assertThat(bookRepository.getAllBooks()).isEqualTo(expectedBooks);
        assertThat(bookRepository.getAllForbiddenBooks()).isEqualTo(expectedForbiddenBooks);
    }

    @Test
    void updateBook_HappyPath() {
        //ARRANGE
        List<BookDto> expectedBooks = List.of(
                new BookDto("1000-2000-3000", "a", "g", "nana", "Lorem Ipsum")
        );

        String requestedBody = "{\"isbn\":\"1000-2000-3000\",\"title\":\"a\",\"authorFirstname\":\"g\",\"authorLastName\":\"nana\",\"summary\":\"g\"}";

        //ACT
        BookDto result = RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("3", "Squarepants")
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .body(requestedBody)
                .when()
                .accept(ContentType.JSON)
                .put("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookDto.class);

        //ASSES
        assertThat(List.of(result)).isEqualTo(expectedBooks);

    }
}