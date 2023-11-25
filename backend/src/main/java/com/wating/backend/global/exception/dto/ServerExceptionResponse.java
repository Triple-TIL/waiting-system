package com.wating.backend.global.exception.dto;

public record ServerExceptionResponse(
        String code,
        String reason
) {
}
