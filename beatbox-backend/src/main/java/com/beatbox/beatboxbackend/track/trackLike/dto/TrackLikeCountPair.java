package com.beatbox.beatboxbackend.track.trackLike.dto;

import com.beatbox.beatboxbackend.track.trackLike.TrackLike;

public record TrackLikeCountPair(
        TrackLike trackLike,
        Long likeCount
) {}
