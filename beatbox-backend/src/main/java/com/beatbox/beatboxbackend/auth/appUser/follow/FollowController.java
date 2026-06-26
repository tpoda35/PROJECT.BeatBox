package com.beatbox.beatboxbackend.auth.appUser.follow;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/me/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final AppUserService appUserService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> follow(@PathVariable UUID userId) {
        AppUser follower = appUserService.getLoggedInUser();
        followService.follow(follower.getId(), userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> unfollow(@PathVariable UUID userId) {
        AppUser follower = appUserService.getLoggedInUser();
        followService.unfollow(follower.getId(), userId);
        return ResponseEntity.noContent().build();
    }

}
