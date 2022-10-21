package com.bizzybees.bizzybooky.domain.dto;

public class BookDto {

    private String ISBN;
    private String title;
    private String authorFirstName;
    private String authorLastName;

    public BookDto(String ISBN, String title, String authorFirstName, String authorLastName) {
        this.ISBN = ISBN;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
    }

    public String getISBN() {
        return ISBN;
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
