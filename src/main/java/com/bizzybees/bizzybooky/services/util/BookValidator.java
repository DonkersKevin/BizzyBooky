package com.bizzybees.bizzybooky.services.util;

import com.bizzybees.bizzybooky.domain.dto.BookDto;

public class BookValidator {
    public void checkRequiredFields(BookDto bookDto) {
        if (bookDto.getIsbn() == null || bookDto.getIsbn().equals("")) {
            throw new IllegalArgumentException("Provide an ISBN number address please!");
        }
        if (bookDto.getTitle() == null || bookDto.getTitle().equals("")) {
            throw new IllegalArgumentException("Provide a title please!");
        }
        if (bookDto.getAuthorLastName() == null || bookDto.getAuthorLastName().equals("")) {
            throw new IllegalArgumentException("Provide a lastname please!");
        }
    }
}
