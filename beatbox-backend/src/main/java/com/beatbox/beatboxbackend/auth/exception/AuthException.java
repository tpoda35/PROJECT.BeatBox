package com.beatbox.beatboxbackend.auth.exception;

public class AuthException extends RuntimeException {
    public AuthException() {
        super("Unauthenticated, log in again");
    }

    public AuthException(String message) {
        super(message);
    }
}
