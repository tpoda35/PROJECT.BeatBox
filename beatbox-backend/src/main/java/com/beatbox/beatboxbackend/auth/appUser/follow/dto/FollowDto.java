package com.beatbox.beatboxbackend.auth.appUser.follow.dto;

import java.util.UUID;

public record FollowDto(
    UUID followerId,
    UUID followedId
) {}
