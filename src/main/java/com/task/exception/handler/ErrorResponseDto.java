package com.task.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private List<ErrorFieldResponseDto> errors;
}
