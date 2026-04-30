package com.beatbox.beatboxbackend.auth.appUser.exception;

public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException() {
        super("User not found");
    }

    public AppUserNotFoundException(String message) {
        super(message);
    }
}
