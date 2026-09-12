package com.example.user.exception;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(String error, Map<String, String> errors) {

    public ErrorResponse(String error) {
        this(error, Map.of());
    }
}
