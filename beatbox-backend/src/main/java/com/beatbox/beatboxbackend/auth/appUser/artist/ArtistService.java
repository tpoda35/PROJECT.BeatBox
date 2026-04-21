package com.beatbox.beatboxbackend.auth.appUser.artist;

import com.beatbox.beatboxbackend.auth.appUser.artist.dto.ArtistDto;

import java.util.List;

public interface ArtistService {
    List<ArtistDto> getRecommendedArtists();
}
