package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.services.RentalService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentalControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    RentalService rentalService;


    @DirtiesContext
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

        assertThat(result).isEqualTo(LocalDate.now().plusWeeks(3));

        //TODO What do we give back when the list is empty?
    }

    @DirtiesContext
    @Test
    void getBookReturnHappyPath_correctMessageDisplay() {
        //given
        BookRental bookrental = rentalService.rentBook("1", "1000-2000-3000");
        String lendIDTest = bookrental.getLendingID();
        //when
        String result = RestAssured
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
                .response()
                .body()
                .print();
        //Then
        assertThat(result).isEqualTo("Thank you for renting books with us!");
    }


    @DirtiesContext
    @Test
    void getBookReturnHappyPath_bookIsMadeAvailable() {
        //given
        BookRental bookrental = rentalService.rentBook("1", "1000-2000-3000");
        String lendIDTest = bookrental.getLendingID();
        rentalService.returnBook(lendIDTest);

        //when
        /** -- Given that the DTO are given back (which do not include a "isAvailable field) there is no point in looking through the webapp.
         */

        //Then
        Book returnedBook = rentalService.getBookRepository().getBookDetailsByIsbn("1000-2000-3000");
        assertTrue(returnedBook.getIsAvailableForRent());
    }

    @DirtiesContext
    @Test
    void getBookReturn_ExceptionThrownWithInvalidLendingID() {
        //given
        BookRental bookrental = rentalService.rentBook("1", "1000-2000-3000");
        String lendIDTest = "random and wrong ID";
        //when
        RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/" + lendIDTest + "/return")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("This lending ID is not attributed"));
    }

    @DirtiesContext
    @Test
    void returnBookTwice_ExceptionThrownAtSecondReturnTry() {
        //given
        BookRental bookrental = rentalService.rentBook("1", "1000-2000-3000");
        String lendIDTest = bookrental.getLendingID();
        rentalService.returnBook(lendIDTest);

        //when
        Book returnedBook = rentalService.getBookRepository().getBookDetailsByIsbn("1000-2000-3000");
        assertTrue(returnedBook.getIsAvailableForRent());

        //then
        RestAssured
                .given()
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/" + lendIDTest + "/return")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("This lending ID is not attributed"));
    }

    //Basic MzpTcXVhcmVwYW50cw==

    @DirtiesContext
    @Test
    void normalMemberTriesToViewOverDueBooks() {

        RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("1","Squarepants")
                .baseUri("http://localhost")
                .port(port)
                .when()
                .accept(ContentType.JSON)
                .get("/books/viewoverdue")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();

    }


}
