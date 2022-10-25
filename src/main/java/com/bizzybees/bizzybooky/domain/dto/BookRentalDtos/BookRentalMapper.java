package com.bizzybees.bizzybooky.domain.dto.BookRentalDtos;

import com.bizzybees.bizzybooky.domain.Book;
import com.bizzybees.bizzybooky.domain.BookRental;

import java.util.List;
import java.util.stream.Collectors;

public class BookRentalMapper {
    public BookRentalDto BookRentalToBookRentalDto(BookRental bookRental) {
        return new BookRentalDto(bookRental.getMemberID(), bookRental.getBookISBN(), bookRental.getDueDate(), bookRental.getLendingID());
    }

    public List<BookRentalDto> BookRentalListToDtoList(List<BookRental> bookRentals) {
        return bookRentals.stream().map(rental -> BookRentalToBookRentalDto(rental)).collect(Collectors.toList());
    }
}