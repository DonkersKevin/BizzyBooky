package com.bizzybees.bizzybooky.domain;

import java.util.Objects;

public class Book {
    private String isbn;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String summary;
    private boolean isAvailableForRent = true;

    public Book(String isbn, String title, String authorFirstName, String authorLastName, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.summary = summary;
    }

    public boolean getIsAvailableForRent() {
        return isAvailableForRent;
    }

    public void setAvailableForRent(boolean availableForRent) {
        isAvailableForRent = availableForRent;
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

    @Override
    public String toString() {
        return "Book{" +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
