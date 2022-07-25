package com.example.accidentsRS.facade.validator.impl;

import com.example.accidentsRS.exceptions.ValidationException;
import com.example.accidentsRS.facade.validator.FieldValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class DefaultFieldValidator<T> implements FieldValidator<T> {

    @Override
    public void validate(Map<String, Object> fields, T instance) throws ValidationException {
        final List<String> validationErrors = new ArrayList<>();
        final List<Field> classFields = Arrays.asList(instance.getClass().getDeclaredFields());

        fields.forEach((fieldName, fieldValue) -> {
            Optional<Field> matchingField = classFields.stream()
                    .filter(classField -> classField.getName().equals(fieldName))
                    .findFirst();

            if (matchingField.isPresent()) {
                if (matchingField.get().getClass() != fieldValue.getClass()) {
                    validationErrors.add("Field {"
                            + fieldName
                            + "} has incorrect type {"
                            + fieldValue.getClass().getName()
                            + "}, expected {"
                            + matchingField.get().getClass().getName()
                            + "}!"
                    );
                }
            } else {
                validationErrors.add(
                        "Field {" + fieldName + "} does not exist in {" + instance.getClass().getName() + "}!"
                );
            }
        });

        if (!CollectionUtils.isEmpty(validationErrors)) {
            throw new ValidationException(validationErrors);
        }
    }

}
