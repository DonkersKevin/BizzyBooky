package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookDto;
import com.bizzybees.bizzybooky.security.Feature;
import com.bizzybees.bizzybooky.security.SecurityService;
import com.bizzybees.bizzybooky.services.RentalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class RentalController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    SecurityService securityService;


    @Autowired
    RentalService rentalService;


    public RentalController(SecurityService securityService, RentalService rentalService) {
        this.securityService = securityService;
        this.rentalService = rentalService;
    }

    // Check if we can return a bookRentalDto
    @GetMapping(path = "/{memberId}/{isbn}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookRentalDto rentBook(@RequestHeader String authorization, @PathVariable String memberId, @PathVariable String isbn) {
        log.info(memberId + "has rented the following book: "+ isbn);
        securityService.validateAuthorization(authorization, Feature.RENT_BOOK);
        return rentalService.rentBook(memberId, isbn);
    }

    @GetMapping(path = "/{lendingId}/return", produces = MediaType.APPLICATION_JSON_VALUE)
    public String returnBook(@RequestHeader String authorization, @PathVariable String lendingId) {
        log.info("The rental has been turned in: " + lendingId);
        securityService.validateAuthorization(authorization, Feature.RETURN_BOOK);
        return rentalService.returnBook(lendingId);
    }


    //ToDo Might need to change the path for restful compliance
    @GetMapping(path = "/{memberId}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> returnLentBooksOfMember (@RequestHeader String authorization, @PathVariable String memberId) {
        log.info("Looking for all borrowed books by: " + memberId);
        securityService.validateAuthorization(authorization, Feature.VIEW_LENT_BOOKS_OF_MEMBER);
        return rentalService.getLentBooksOfMember(memberId);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/viewoverdue", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAllOverdueBooks(@RequestHeader String authorization) {
        log.info("Returning all overdue books");
        securityService.validateAuthorization(authorization, Feature.VIEW_OVERDUE_BOOKS);
        return rentalService.getAllBooksThatAreOverdue();

    }
}



