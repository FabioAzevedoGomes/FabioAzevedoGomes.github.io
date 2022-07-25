package com.example.accidentsRS.facade.validator;

import com.example.accidentsRS.exceptions.ValidationException;

import java.util.Map;

public interface FieldValidator<T> {
    void validate(Map<String, Object> fields, T instance) throws ValidationException;
}
