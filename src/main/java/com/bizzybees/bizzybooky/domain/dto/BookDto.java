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
}
