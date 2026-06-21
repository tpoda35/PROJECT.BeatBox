package com.beatbox.beatboxbackend.auth.appUser;

import com.beatbox.beatboxbackend.auth.appUser.dto.ArtistDto;
import com.beatbox.beatboxbackend.track.trackLike.dto.TrackLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class MeController {

    private final AppUserService appUserService;

    // Public endpoint
    @GetMapping("/recommended-artists")
    public List<ArtistDto> getRecommendedArtists() {
        return appUserService.getRecommendedArtists();
    }

    @GetMapping("/liked-tracks")
    public Page<TrackLikeDto> getLikedTracks(
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "3") int pageSize
    ) {
        return appUserService.getLikedTracks(pageNum, pageSize);
    }

}
