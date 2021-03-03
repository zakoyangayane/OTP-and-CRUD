package com.task.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ValidationError implements ErrorCode {
  VALIDATION_ERROR(HttpStatus.BAD_REQUEST);

  private final HttpStatus status;

  @Override
  public HttpStatus getStatus() {
    return status;
  }
}
