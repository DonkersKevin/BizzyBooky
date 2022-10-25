package com.bizzybees.bizzybooky.domain.dto.BookRentalDtos;

import com.bizzybees.bizzybooky.domain.BookRental;

public class BookRentalMapper {
    public BookRentalDto BookRentalToBookRentalDto(BookRental bookRental) {
        return new BookRentalDto(bookRental.getMemberID(), bookRental.getBookISBN(), bookRental.getDueDate(), bookRental.getLendingID());
    }
}
