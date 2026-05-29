package com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto;

import com.beatbox.beatboxbackend.track.dto.TrackDto;

import java.time.Instant;

public record ListeningHistoryDto(
        TrackDto trackDto,
        Instant createdAt
) {}
