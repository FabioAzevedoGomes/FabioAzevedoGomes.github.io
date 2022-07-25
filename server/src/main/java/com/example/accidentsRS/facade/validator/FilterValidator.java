package com.example.accidentsRS.facade.validator;

import com.example.accidentsRS.data.FilterWrapperData;
import com.example.accidentsRS.exceptions.ValidationException;

import java.util.List;

public interface FilterValidator<T> {
    void validate(List<FilterWrapperData> filters, T instance) throws ValidationException;
}
