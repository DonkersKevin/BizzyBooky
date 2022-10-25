package com.bizzybees.bizzybooky.domain.dto.BookRentalDtos;

import java.time.LocalDate;
import java.util.UUID;

public class BookRentalDto {
    private String lendingID;
    private LocalDate dueDate;
    private String memberID;
    private String bookISBN;

    public BookRentalDto(String memberID, String bookISBN, LocalDate dueDate, String lendingID) {
        this.lendingID = lendingID;
        this.dueDate = dueDate;
        this.memberID = memberID;
        this.bookISBN = bookISBN;
    }

    public String getLendingID() {
        return lendingID;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getBookISBN() {
        return bookISBN;
    }
}
