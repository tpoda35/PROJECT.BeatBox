package com.beatbox.beatboxbackend.follow;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.exception.AppUserNotFoundException;
import com.beatbox.beatboxbackend.auth.appUser.AppUserRepository;
import com.beatbox.beatboxbackend.follow.exception.AlreadyFollowingException;
import com.beatbox.beatboxbackend.follow.exception.NotFollowingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.beatbox.beatboxbackend.follow.FollowMapper.createFollow;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Follow> getFollows(UUID userId) {
        return List.of();
    }

    @Transactional
    @Override
    public void follow(UUID followerId, UUID followingId) {
        AppUser follower = findAppUser(followerId);
        AppUser following = findAppUser(followingId);

        try {
            followRepository.save(createFollow(follower, following));
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyFollowingException();
        }
    }

    @Transactional
    @Override
    public void unfollow(UUID followerId, UUID followingId) {
        int deleted = followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);

        if (deleted == 0) {
            throw new NotFollowingException();
        }
    }

    private AppUser findAppUser(UUID appUserId) {
        return appUserRepository.findById(appUserId)
                .orElseThrow(AppUserNotFoundException::new);
    }
}
