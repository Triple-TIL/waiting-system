package com.wating.backend.global.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException{

    private HttpStatus httpStatus;
    private String code;
    private String message;

    public ApplicationException(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}
