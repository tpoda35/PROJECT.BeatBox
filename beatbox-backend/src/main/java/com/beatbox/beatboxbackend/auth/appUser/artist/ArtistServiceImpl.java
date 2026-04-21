package com.beatbox.beatboxbackend.auth.appUser.artist;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.AppUserRepository;
import com.beatbox.beatboxbackend.auth.appUser.AppUserService;
import com.beatbox.beatboxbackend.auth.appUser.artist.dto.ArtistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    @Override
    public List<ArtistDto> getRecommendedArtists() {
        AppUser loggedInUser = appUserService.getLoggedInUser();

        int limit = 3;

        List<AppUser> users = appUserRepository
                .findAll(PageRequest.of(0, limit))
                .getContent();

        List<UUID> ids = users.stream()
                .map(AppUser::getId)
                .toList();

        return appUserRepository.findArtistsWithStatsByIds(ids, loggedInUser.getId());
    }
}
