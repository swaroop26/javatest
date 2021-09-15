package com.altx.javatest;

public class MovieNotFoundException extends RuntimeException {
    MovieNotFoundException(Long id) {
        super("Could not find movie " + id);
    }
}
