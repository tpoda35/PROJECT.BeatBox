package com.beatbox.beatboxbackend.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Override
    public List<Follow> getFollows(UUID userId) {
        return List.of();
    }

    @Override
    public void follow(UUID followerId, UUID followedId) {

    }

    @Override
    public void unfollow(UUID followerId, UUID followedId) {

    }
}
