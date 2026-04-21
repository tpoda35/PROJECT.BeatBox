package com.beatbox.beatboxbackend.auth.appUser.artist;

import com.beatbox.beatboxbackend.auth.appUser.artist.dto.ArtistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/recommended")
    public List<ArtistDto> getRecommendedArtists() {
        return artistService.getRecommendedArtists();
    }

}
