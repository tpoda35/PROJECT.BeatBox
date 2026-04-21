package com.beatbox.beatboxbackend.follow.dto;

import java.util.UUID;

public record FollowDto(
    UUID followerId,
    UUID followedId
) {}
