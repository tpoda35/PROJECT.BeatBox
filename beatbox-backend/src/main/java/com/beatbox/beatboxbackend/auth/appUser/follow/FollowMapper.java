package com.beatbox.beatboxbackend.auth.appUser.follow;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;

public class FollowMapper {

    public static Follow createFollow(AppUser follower, AppUser following) {
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }

}
