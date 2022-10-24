package com.bizzybees.bizzybooky.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private String isbn;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String summary;


    public BookDto(String isbn, String title, String authorFirstName, String authorLastName, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.summary = summary;
    }

    public BookDto(String isbn, String title, String authorFirstName, String authorLastName) {
        this.isbn = isbn;
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

    public String getSummary() {
        return summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDto bookDto)) return false;

        return getISBN() != null ? getISBN().equals(bookDto.getISBN()) : bookDto.getISBN() == null;
    }

    @Override
    public int hashCode() {
        return getISBN() != null ? getISBN().hashCode() : 0;
    }


    @Override
    public String toString() {
        return "BookDto{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
