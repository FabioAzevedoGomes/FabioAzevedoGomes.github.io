package com.example.accidentsRS.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad data")
public class PersistenceException extends Exception {

    private String message;
    private Throwable reason;

    public PersistenceException(final String message) {
        super();
        this.message = message;
    }

    public PersistenceException(final String message, final Throwable reason) {
        super();
        this.message = message;
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getReason() {
        return reason;
    }

    public void setReason(Throwable reason) {
        this.reason = reason;
    }
}
