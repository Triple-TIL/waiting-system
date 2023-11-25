package com.wating.backend.global.exception.constant;

import com.wating.backend.global.exception.custom.ApplicationException;
import org.springframework.http.HttpStatus;

public enum ErrorCode {

    QUEUE_ALREADY_REGISTERED_USER(HttpStatus.CONFLICT, "UQ-0001", "Already registered in queue"),
    QUEUE_ALREADY_REGISTERED_USER2(HttpStatus.CONFLICT, "UQ-0001", "Already registered in %s");

    private final HttpStatus httpStatus;
    private final String code;
    private final String reason;

    ErrorCode(HttpStatus httpStatus, String code, String reason) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.reason = reason;
    }

    public ApplicationException build() {
        return new ApplicationException(httpStatus, code, reason);
    }

    public ApplicationException build(Object ...args) {
        return new ApplicationException(httpStatus, code, reason.formatted(args));
    }

}
