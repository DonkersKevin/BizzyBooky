package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookDto;
import com.bizzybees.bizzybooky.domain.dto.BookRentalDtos.BookRentalDto;
import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookMapper;
import com.bizzybees.bizzybooky.security.Feature;
import com.bizzybees.bizzybooky.security.SecurityService;
import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookDto;
import com.bizzybees.bizzybooky.domain.dto.BookRentalDtos.BookRentalDto;
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
    @GetMapping(path = "/{id}/{isbn}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookRentalDto rentBook(@PathVariable String id, @PathVariable String isbn) {
        return rentalService.rentBook(id, isbn);

    }

    @GetMapping(path = "/{lendingId}/return", produces = MediaType.APPLICATION_JSON_VALUE)
    public String returnBook(@PathVariable String lendingId) {
        return rentalService.returnBook(lendingId);
    }


    @GetMapping(path = "/{memberId}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> returnLentBooksOfMember (@RequestHeader(required = false) String authorization, @PathVariable String memberId) {
        //securityService.validateAuthorization(authorization, Feature.RETURN_LENT_BOOK);
        return rentalService.getLentBooksOfMember(memberId);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/viewoverdue", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> getAllOverdueBooks(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_OVERDUE_BOOKS);
        return rentalService.getAllBooksThatAreOverdue();

    }
}



