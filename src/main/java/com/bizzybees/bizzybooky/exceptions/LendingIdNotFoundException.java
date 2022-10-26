package com.bizzybees.bizzybooky.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class LendingIdNotFoundException extends RuntimeException {
}
