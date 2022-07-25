package com.example.accidentsRS.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractEndpoint {
    protected ResponseEntity getServerErrorResponse() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
