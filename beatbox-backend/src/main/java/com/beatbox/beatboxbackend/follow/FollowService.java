package com.beatbox.beatboxbackend.follow;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    List<Follow> getFollows(UUID userId);
    void follow(UUID followerId, UUID followedId);
    void unfollow(UUID followerId, UUID followedId);
}
