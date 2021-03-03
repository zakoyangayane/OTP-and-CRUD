package com.task.exception;

import com.task.exception.handler.ErrorCode;
import com.task.exception.handler.ErrorFieldResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;
    private List<ErrorFieldResponseDto> errors;


    public BaseException(ErrorCode errorCode, List<ErrorFieldResponseDto> errors) {
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public BaseException(String message) {
        super(message);
    }
}
