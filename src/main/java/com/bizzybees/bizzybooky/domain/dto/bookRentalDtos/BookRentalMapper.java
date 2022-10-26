package com.bizzybees.bizzybooky.domain.dto.bookRentalDtos;

import com.bizzybees.bizzybooky.domain.BookRental;

import java.util.List;
import java.util.stream.Collectors;

public class BookRentalMapper {
    public BookRentalDto BookRentalToBookRentalDto(BookRental bookRental) {
        return new BookRentalDto(bookRental.getMemberID(), bookRental.getBookISBN(), bookRental.getDueDate(), bookRental.getLendingID());
    }


}
