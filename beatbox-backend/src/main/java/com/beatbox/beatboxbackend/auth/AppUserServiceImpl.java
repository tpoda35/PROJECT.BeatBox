package com.beatbox.beatboxbackend.auth;

import com.beatbox.beatboxbackend.auth.dto.RecommendedArtistsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.beatbox.beatboxbackend.auth.AppUserMapper.createAppUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public AppUser getLoggedInUser() {
        return findAppUser(getUserIdFromContextHolder());
    }

    @Transactional
    @Override
    public AppUser getOrCreateAppUser(UUID keycloakId, String preferredUsername) {
        return appUserRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    try {
                        return createUser(keycloakId, preferredUsername);
                    } catch (DataIntegrityViolationException e) {
                        // Race condition: another thread created it
                        return appUserRepository.findByKeycloakId(keycloakId)
                                .orElseThrow(() -> new IllegalStateException(
                                        "User creation failed unexpectedly"));
                    }
                });
    }

    // This method searches for 3 random users, and fetches their stats
    // on the repository layer, with a query.
    @Override
    public List<RecommendedArtistsDto> getRecommendedArtists() {
        int limit = 3;

        List<AppUser> users = appUserRepository
                .findAll(PageRequest.of(0, limit))
                .getContent();

        List<UUID> ids = users.stream()
                .map(AppUser::getId)
                .toList();

        return appUserRepository.findArtistsWithStatsByIds(ids);
    }

    private UUID getUserIdFromContextHolder() {
        Map<String, Object> claims = getClaimsFromJwt();

        return UUID.fromString((String) claims.get("sub"));
    }

    private Map<String, Object> getClaimsFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthorizationDeniedException("Unauthorized, log in again.");
        }

        if (!(authentication instanceof JwtAuthenticationToken jwtToken)) {
            throw new AuthorizationDeniedException("Invalid authentication token.");
        }

        return jwtToken.getToken().getClaims();
    }

    private AppUser findAppUser(UUID keycloakId) {
        return appUserRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> {
                    log.warn("User not found with the id of {}.", keycloakId);
                    return new AppUserNotFoundException("User not found.");
                });
    }

    private AppUser createUser(UUID keycloakId, String preferredUsername) {
        AppUser appUser = createAppUser(keycloakId, preferredUsername);
        return appUserRepository.save(appUser);
    }
}
