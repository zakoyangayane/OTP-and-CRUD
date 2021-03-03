package com.task.exception.handler;

import org.springframework.http.HttpStatus;

public interface ErrorCode {


    HttpStatus getStatus();

}
