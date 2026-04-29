package com.beatbox.beatboxbackend.track.dto;

import java.util.List;
import java.util.UUID;

public record TrackDto(
        UUID trackId,
        String title,
        List<String> artists
) {}
