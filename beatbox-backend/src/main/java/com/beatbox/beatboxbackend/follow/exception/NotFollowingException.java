package com.beatbox.beatboxbackend.follow.exception;

public class NotFollowingException extends RuntimeException {
    public NotFollowingException() {
        super("You are not following the user");
    }

    public NotFollowingException(String message) {
        super(message);
    }
}
