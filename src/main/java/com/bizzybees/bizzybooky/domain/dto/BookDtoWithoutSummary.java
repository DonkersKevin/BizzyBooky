package com.bizzybees.bizzybooky.domain.dto;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDtoWithoutSummary that)) return false;

        return getIsbn() != null ? getIsbn().equals(that.getIsbn()) : that.getIsbn() == null;
    }

    @Override
    public int hashCode() {
        return getIsbn() != null ? getIsbn().hashCode() : 0;
    }
}
