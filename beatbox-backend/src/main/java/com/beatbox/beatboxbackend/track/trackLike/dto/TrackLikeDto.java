package com.beatbox.beatboxbackend.track.trackLike.dto;

import com.beatbox.beatboxbackend.track.dto.TrackDto;

import java.time.Instant;

public record TrackLikeDto (
        TrackDto trackDto,
        Instant createdAt
) {}
