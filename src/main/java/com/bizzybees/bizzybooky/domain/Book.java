package com.bizzybees.bizzybooky.domain;

public class Book {
    private String id;
    private String isbn;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String summary;

    public Book(String id, String isbn, String title, String authorFirstName, String authorLastName, String summary) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.summary = summary;
    }

    public String getId() {
        return id;
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

    public String getSummary() {
        return summary;
    }
}
