package com.altx.javatest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Actor Not Found")
public class ActorNotFoundException extends RuntimeException {
    public ActorNotFoundException(Long id) {
        super("Could not find the actor " + id);
    }
}
