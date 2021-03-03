package com.task.validator;

import com.task.exception.BaseException;
import com.task.exception.handler.ErrorFieldResponseDto;
import com.task.exception.handler.ValidationError;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestFieldsValidator {

    public void validate(final Errors errors) {

        List<ErrorFieldResponseDto> errorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> {
                ErrorFieldResponseDto fieldError = new ErrorFieldResponseDto();
                fieldError.setField(error.getObjectName());
                fieldError.setMessage(error.getDefaultMessage());

                errorList.add(fieldError);
            });
            throw new BaseException(ValidationError.VALIDATION_ERROR, errorList);
        }
    }


    public void validate(final Object obj) {

        if (obj == null) {
            throw new BaseException("Please send correct data");
        }
    }
}
