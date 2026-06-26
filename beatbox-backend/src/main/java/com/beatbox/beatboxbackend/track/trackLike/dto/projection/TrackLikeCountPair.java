package com.beatbox.beatboxbackend.track.trackLike.dto.projection;

import com.beatbox.beatboxbackend.track.trackLike.TrackLike;

public record TrackLikeCountPair(
        TrackLike trackLike,
        Long likeCount
) {}
