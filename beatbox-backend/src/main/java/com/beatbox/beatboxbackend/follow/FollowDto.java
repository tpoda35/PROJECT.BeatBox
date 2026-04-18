package com.beatbox.beatboxbackend.follow;

import java.util.UUID;

public record FollowDto(
    UUID followerId,
    UUID followedId
) {}
