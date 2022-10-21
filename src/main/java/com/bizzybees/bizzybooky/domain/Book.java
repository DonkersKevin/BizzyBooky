package com.bizzybees.bizzybooky.domain;

public class Book {
    private String isbn;
    private String title;
    private String authorFirstName;
    private String authorLastName;

    public Book(String ISBN, String title, String authorFirstName, String authorLastName) {
        this.isbn = ISBN;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
    }

    public String getISBN() {
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
