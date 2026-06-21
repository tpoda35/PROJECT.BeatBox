package com.beatbox.beatboxbackend.auth.appUser;

import com.beatbox.beatboxbackend.auth.appUser.dto.ArtistDto;
import com.beatbox.beatboxbackend.track.trackLike.dto.TrackLikeDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppUserService {
    AppUser getLoggedInUser();
    Optional<AppUser> getLoggedInUserOptional();
    UUID getLoggedInUserId();
    void seedAppUser(UUID keycloakId, String preferredUsername);

    List<ArtistDto> getRecommendedArtists();
    Page<TrackLikeDto> getLikedTracks(int pageNum, int pageSize);
}
