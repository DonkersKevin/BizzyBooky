package com.bizzybees.bizzybooky.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

public class BookRentalDto {
    private String lendingID;
    private LocalDate dueDate;
    private String memberID;
    private String bookISBN;

    public BookRentalDto( String memberID, String bookISBN,LocalDate dueDate,String lendingID) {
        this.lendingID = lendingID;
        this.dueDate = dueDate;
        this.memberID = memberID;
        this.bookISBN = bookISBN;
    }
}
