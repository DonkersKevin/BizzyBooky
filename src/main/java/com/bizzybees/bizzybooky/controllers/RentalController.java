package com.bizzybees.bizzybooky.controllers;

import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.BookDto;
import com.bizzybees.bizzybooky.domain.dto.BookRentalDto;
import com.bizzybees.bizzybooky.services.RentalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class RentalController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

// CHeck if we can return a bookRentalDto
    @GetMapping(path = "/{id}/{isbn}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookRental rentBook(@PathVariable String id, @PathVariable String isbn) {
        return rentalService.rentBook(id, isbn);

    }

    @GetMapping(path = "/{lendingId}/return", produces = MediaType.APPLICATION_JSON_VALUE)
    public String returnBook(@PathVariable String lendingId) {
        return rentalService.returnBook(lendingId);
    }


    @GetMapping(path = "/{memberId}/lent", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> returnLentBooksOfMember (@PathVariable String memberId) {
        //add librarian check
        return rentalService.getLentBooksOfMember(memberId);
    }
}



