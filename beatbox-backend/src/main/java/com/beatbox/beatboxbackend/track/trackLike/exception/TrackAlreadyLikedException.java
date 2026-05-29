package com.beatbox.beatboxbackend.track.trackLike.exception;

public class TrackAlreadyLikedException extends RuntimeException {
    public TrackAlreadyLikedException() {
        super("Track already isLiked");
    }

    public TrackAlreadyLikedException(String message) {
        super(message);
    }
}
