package com.beatbox.beatboxbackend.auth.dto;

import java.util.UUID;

public record RecommendedArtistsDto(
        UUID artistId,
        String preferredUsername,
        Long followerCount,
        Long trackCount
) {}
