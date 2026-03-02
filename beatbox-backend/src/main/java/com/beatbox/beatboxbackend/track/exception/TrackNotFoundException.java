package com.beatbox.beatboxbackend.track.exception;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException() {
        super("Track not found");
    }

    public TrackNotFoundException(String message) {
        super(message);
    }
}
