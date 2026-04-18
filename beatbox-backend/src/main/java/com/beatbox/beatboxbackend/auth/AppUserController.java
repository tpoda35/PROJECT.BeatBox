package com.beatbox.beatboxbackend.auth;

import com.beatbox.beatboxbackend.auth.dto.RecommendedArtistsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/api/artists/recommended")
    public List<RecommendedArtistsDto> getRecommendedArtists() {
        return appUserService.getRecommendedArtists();
    }

}
