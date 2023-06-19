package com.webtracker.app.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class ApiException {

    private final String exceptionMessage;
    private final HttpStatus httpStatus;
    private final List<String> errors;
    private final LocalDateTime timestamp;
}