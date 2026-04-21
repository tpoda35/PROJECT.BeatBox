package com.beatbox.beatboxbackend.auth.appUser;

public class AppUserNotFoundException extends RuntimeException {
    public AppUserNotFoundException() {
        super("User not found");
    }

    public AppUserNotFoundException(String message) {
        super(message);
    }
}
