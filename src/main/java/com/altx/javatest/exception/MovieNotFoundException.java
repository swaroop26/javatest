package com.altx.javatest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Movie Not Found")
public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long id) {
        super("Could not find movie " + id);
    }
}
