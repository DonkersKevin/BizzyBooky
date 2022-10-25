package com.bizzybees.bizzybooky.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;


@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    protected void bookNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "No book by that isbn...");
    }

    @ExceptionHandler(TitleNotFoundException.class)
    protected void TitleNotFoundException(TitleNotFoundException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "No book by that title...");
    }

    @ExceptionHandler(IsbnNotFoundException.class)
    protected void IsbnNotFoundException(IsbnNotFoundException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "No book by that isbn...");
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    protected void AuthorNotFoundException(AuthorNotFoundException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), "No book by that author...");
    }
}
