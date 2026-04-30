package com.beatbox.beatboxbackend.exception;

import com.beatbox.beatboxbackend.auth.appUser.exception.AppUserNotFoundException;
import com.beatbox.beatboxbackend.follow.exception.AlreadyFollowingException;
import com.beatbox.beatboxbackend.follow.exception.NotFollowingException;
import com.beatbox.beatboxbackend.track.exception.TrackNotFoundException;
import lombok.NonNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Follow exceptions
    @ExceptionHandler(NotFollowingException.class)
    public ResponseEntity<ExceptionDto> handleNotFollowingException(NotFollowingException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new ExceptionDto(
                        ex.getMessage(),
                        OffsetDateTime.now(),
                        BAD_REQUEST.value()
                )
        );
    }

    @ExceptionHandler(AlreadyFollowingException.class)
    public ResponseEntity<ExceptionDto> handleAlreadyFollowingException(AlreadyFollowingException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new ExceptionDto(
                        ex.getMessage(),
                        OffsetDateTime.now(),
                        BAD_REQUEST.value()
                )
        );
    }

    // AppUser exceptions
    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleAppUserNotFoundException(AppUserNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(
                new ExceptionDto(
                        ex.getMessage(),
                        OffsetDateTime.now(),
                        NOT_FOUND.value()
                )
        );
    }

    // Track exceptions
    @ExceptionHandler(TrackNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleTrackNotFoundException(TrackNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(
                new ExceptionDto(
                        ex.getMessage(),
                        OffsetDateTime.now(),
                        NOT_FOUND.value()
                )
        );
    }

    // Method argument exceptions
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        // Extract global errors.
        List<String> globalErrors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        // Extract field errors.
        List<String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        // Combine them.
        List<String> allErrors = new ArrayList<>();
        allErrors.addAll(globalErrors);
        allErrors.addAll(fieldErrors);

        // Return it.
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(Map.of("message", String.join(", ", allErrors)));
    }
}
