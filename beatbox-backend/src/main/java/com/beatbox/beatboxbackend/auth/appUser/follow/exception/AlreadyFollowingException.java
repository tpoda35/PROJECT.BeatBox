package com.beatbox.beatboxbackend.auth.appUser.follow.exception;

public class AlreadyFollowingException extends RuntimeException {
    public AlreadyFollowingException() {
        super("You already following the user");
    }

    public AlreadyFollowingException(String message) {
        super(message);
    }
}
