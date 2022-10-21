package com.bizzybees.bizzybooky.domain;

import java.time.LocalDate;
import java.util.UUID;

public class BookRental {
    private String lendingID;
    private LocalDate dueDate;
    private String memberID;
    private String bookISBN;

    public BookRental( String memberID, String bookISBN) {
        this.lendingID = UUID.randomUUID().toString();
        this.dueDate = LocalDate.now().plusWeeks(3);
        this.memberID = memberID;
        this.bookISBN = bookISBN;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
