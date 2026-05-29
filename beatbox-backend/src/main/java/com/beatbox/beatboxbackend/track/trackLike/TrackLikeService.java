package com.beatbox.beatboxbackend.track.trackLike;

import com.beatbox.beatboxbackend.track.Track;

import java.util.UUID;

public interface TrackLikeService {
    void likeTrack(UUID trackId);
    void unlikeTrack(UUID trackId);
    Long getLikeCount(Track track);
}
