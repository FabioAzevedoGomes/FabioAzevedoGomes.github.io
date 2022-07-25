package com.example.accidentsRS.facade.validator.impl;

import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.facade.validator.FilterValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultFilterValidator<T> implements FilterValidator<T> {

    @Override
    public void validate(List<FilterWrapperData> filters, T instance) throws ValidationException {
        // TODO
    }
}
