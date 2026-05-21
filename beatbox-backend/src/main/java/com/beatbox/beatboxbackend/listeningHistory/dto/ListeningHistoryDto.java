package com.beatbox.beatboxbackend.listeningHistory.dto;

import com.beatbox.beatboxbackend.track.dto.TrackDto;

import java.time.Instant;

public record ListeningHistoryDto(
        TrackDto trackDto,
        Instant listenedAt
) {}
