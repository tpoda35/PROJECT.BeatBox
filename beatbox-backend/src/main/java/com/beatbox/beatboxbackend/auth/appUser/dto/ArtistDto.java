package com.beatbox.beatboxbackend.auth.appUser.dto;

import java.util.UUID;

public record ArtistDto(
        UUID artistId,
        String preferredUsername,
        Long followerCount,
        Long trackCount,
        Boolean isVerified,
        Boolean isFollowing // True: following the logged-in user from the api call, otherwise not
) {}
