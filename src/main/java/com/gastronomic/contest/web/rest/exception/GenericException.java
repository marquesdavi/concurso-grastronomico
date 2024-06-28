package com.gastronomic.contest.web.rest.exception;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {

    private HttpStatus httpStatus;

    public GenericException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
