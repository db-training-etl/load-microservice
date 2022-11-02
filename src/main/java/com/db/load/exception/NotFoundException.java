package com.db.load.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class NotFoundException extends DomainException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(message);
    }
}