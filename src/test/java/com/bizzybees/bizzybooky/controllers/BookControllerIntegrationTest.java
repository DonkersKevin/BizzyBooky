package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.repositories.RentalRepository;
import com.bizzybees.bizzybooky.services.RentalService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void GetBooks_HappyPath() {
        //ARRANGE
        ArrayList<BookDto> expectedBookListWithoutSummary = new ArrayList<>(List.of(
                new BookDto("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
                new BookDto("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new BookDto("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum"),
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

    /*
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

     */

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

    //TODO fix method name, and possibly add exception. Also implement properly
    @Test
    void GivenMalformedIsbn_ThrowXXXException() {
        RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/8")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("Malformed Isbn detected"));
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
    void authorSearch_WildCardHappyPath() {
        //ARRANGE
        List<BookDto> expectedBooks = List.of(
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
        assertThat(List.of(result)).isEqualTo(expectedBooks);
    }

    @Test
    void authorSearch_WhenNotFound_ThrowException() {
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
                .get("/books?author=*z*")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("No book by that author..."));

        //ASSES
    }


    @Test
    void getRentalHappyPath() {
        //given
        BookRental bookRentalExpected = new BookRental("1", "1000-2000-3000");

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

        assertThat(result).isEqualTo(LocalDate.of(2022, 11, 14));
<<<<<<< HEAD
=======
        //then
        //Assertions.assertEquals(LocalDate.of(2022,11,11),rental.getDueDate());

        //TODO What do we give back when the list is empty?
    }


    public static void main(String[] args) {
        RentalService rentalService = new RentalService(new RentalRepository(), new BookRepository(), new MemberRepository());
        rentalService.rentBook("1", "1000-2000-3000");
        rentalService.rentBook("2", "2000-3000-4000");
        BookRental bookRental = rentalService.getRentalRepository().getRentalDatabase().values().stream().findFirst().orElseThrow();
        String lendIDTest = bookRental.getLendingID();

        System.out.println(rentalService.getRentalRepository().getRentalDatabase().values());

        System.out.println(rentalService.returnBook(lendIDTest));
        System.out.println(rentalService.getRentalRepository().getRentalDatabase().values());
    }

    @Test
    void getBookReturnHappyPath() {
        //given
        RentalService rentalService = new RentalService(new RentalRepository(), new BookRepository(), new MemberRepository());
        rentalService.rentBook("1", "1000-2000-3000");
        rentalService.rentBook("2", "2000-3000-4000");
        BookRental bookRental = rentalService.getRentalRepository().getRentalDatabase().values().stream().findFirst().orElseThrow();
        String lendIDTest = bookRental.getLendingID();
        String actual = rentalService.returnBook(lendIDTest);


         //when

         Book result = RestAssured
         .given()
         .baseUri("http://localhost")
         .port(port)
         .when()
         .accept(ContentType.JSON)
         .get("/books/" + lendIDTest + "/return")
         .then()
         .assertThat()
         .statusCode(HttpStatus.OK.value())
         .extract()
         .as(BookRepository.class).getBookDetailsByIsbn("1000-2000-3000");

        //Then
        //assertEquals(actual, "Thank you for renting books with us!");
        assertTrue(result.isAvailableForRent());
>>>>>>> eba40cca63b255cc232e802e1869a00ca2518e13
        //then
        //Assertions.assertEquals(LocalDate.of(2022,11,11),rental.getDueDate());

        //TODO What do we give back when the list is empty?
    }
}