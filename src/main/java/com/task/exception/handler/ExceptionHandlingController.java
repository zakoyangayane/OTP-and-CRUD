package com.task.exception.handler;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.task.base.response.BaseResponse;
import com.task.exception.BaseException;
import com.task.exception.NotFoundException;
import com.task.exception.NotReadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<ErrorResponseDto>> handleBaseException(BaseException ex) {

        List<ErrorFieldResponseDto> errors = ex.getErrors();
        if (errors == null) {
            errors = new ArrayList<>();
            errors.add(new ErrorFieldResponseDto("requestBody", ex.getMessage()));
        }

        HttpStatus httpStatus;

        if (ex.getErrorCode() == null || ex.getErrorCode().getStatus() == null) {
            httpStatus = HttpStatus.CONFLICT;
        } else {
            httpStatus = ex.getErrorCode().getStatus();
        }

        BaseResponse<ErrorResponseDto> baseResponse = new BaseResponse<>(false, "Validation error.Please check errors object.", new ErrorResponseDto(errors));

        return new ResponseEntity<>(baseResponse, httpStatus);
    }

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<BaseResponse<Object>> handleValueInstantiationException(ValueInstantiationException ex) {

        try {
            Field requiredField = ex.getType().getRawClass().getDeclaredFields()[ex.getLocation().getColumnNr()];

            if (requiredField == null) {
                BaseResponse<Object> baseResponse = new BaseResponse<>(
                        false,
                        "Please set all corresponding fields: ".concat(ex.getMessage()),
                        null);
                return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
            }

            String fieldName = requiredField.getName();
            String errorMessage = "Please set <".concat(fieldName).concat(">: ").concat(fieldName.concat(" is required field"));
            BaseResponse<Object> baseResponse = new BaseResponse<>(false, errorMessage, null);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);

        } catch (Exception e) {

            BaseResponse<Object> baseResponse = new BaseResponse<>(
                    false,
                    "Please set all corresponding field !!!",
                    null);

            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFoundException(NotFoundException error) {

        BaseResponse<Object> baseResponse = new BaseResponse<>(false, error.getMessage(), null);

        return new ResponseEntity<>(baseResponse, error.getHttpStatus());
    }


    @ExceptionHandler(NotReadException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotReadException(NotReadException error) {
        BaseResponse<Object> baseResponse = new BaseResponse<>(false, error.getMessage(), null);
        return new ResponseEntity<>(baseResponse, error.getHttpStatus());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<BaseResponse<Object>> userException(MissingRequestHeaderException ex) {
        BaseResponse<Object> baseResponse = new BaseResponse<>(false, "your Request Is bad", null);
        return new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
    }

}
