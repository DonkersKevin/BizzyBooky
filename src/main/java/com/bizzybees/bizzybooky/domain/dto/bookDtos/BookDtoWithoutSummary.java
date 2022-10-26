package com.bizzybees.bizzybooky.domain.dto.bookDtos;

public class BookDtoWithoutSummary {

    private String isbn;
    private String title;
    private String authorFirstName;
    private String authorLastName;

    public BookDtoWithoutSummary(String isbn, String title, String authorFirstName, String authorLastName) {
        this.isbn = isbn;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }




}
