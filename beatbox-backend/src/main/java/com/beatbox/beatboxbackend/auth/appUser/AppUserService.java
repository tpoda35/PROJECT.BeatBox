package com.beatbox.beatboxbackend.auth.appUser;

import com.beatbox.beatboxbackend.auth.appUser.dto.ArtistDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppUserService {
    AppUser getLoggedInUser();
    Optional<AppUser> getLoggedInUserOptional();
    UUID getLoggedInUserId();
    void seedAppUser(UUID keycloakId, String preferredUsername);

    List<ArtistDto> getRecommendedArtists();
}
