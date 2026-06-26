package com.beatbox.beatboxbackend.track.dto.projection;

import com.beatbox.beatboxbackend.track.Track;

public record TrackRecommendationPair(
        Track track,
        long likeCount,
        boolean isLiked
) {}
