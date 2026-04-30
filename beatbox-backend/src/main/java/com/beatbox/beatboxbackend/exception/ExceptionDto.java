package com.beatbox.beatboxbackend.exception;

import java.time.OffsetDateTime;

public record ExceptionDto(
    String message,
    OffsetDateTime timestamp,
    Integer statusCode
) {}
