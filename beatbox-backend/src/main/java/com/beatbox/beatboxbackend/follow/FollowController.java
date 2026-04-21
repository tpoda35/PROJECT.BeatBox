package com.beatbox.beatboxbackend.follow;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final AppUserService appUserService;

    @PostMapping("/{followingId}")
    public void follow(@PathVariable UUID followingId) {
        AppUser follower = appUserService.getLoggedInUser();

        followService.follow(follower.getId(), followingId);
    }

    @DeleteMapping("/{followingId}")
    public void unfollow(@PathVariable UUID followingId) {
        AppUser follower = appUserService.getLoggedInUser();

        followService.unfollow(follower.getId(), followingId);
    }

}
