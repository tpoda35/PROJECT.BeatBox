package com.beatbox.beatboxbackend.auth;

import com.beatbox.beatboxbackend.auth.dto.RecommendedArtistsDto;

import java.util.List;
import java.util.UUID;

public interface AppUserService {
    AppUser getLoggedInUser();
    AppUser getOrCreateAppUser(UUID keycloakId, String preferredUsername);
    List<RecommendedArtistsDto> getRecommendedArtists();
}
