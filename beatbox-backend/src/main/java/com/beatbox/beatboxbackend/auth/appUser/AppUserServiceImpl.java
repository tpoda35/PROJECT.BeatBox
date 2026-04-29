package com.beatbox.beatboxbackend.auth.appUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.beatbox.beatboxbackend.auth.appUser.AppUserMapper.createAppUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public AppUser getLoggedInUser() {
        UUID userId = getUserIdFromContextHolder()
                .orElseThrow(() -> new AuthorizationDeniedException("Unauthorized, log in again."));
        return findAppUser(userId);
    }

    @Override
    public Optional<AppUser> getLoggedInUserOptional() {
        return getUserIdFromContextHolder()
                .flatMap(appUserRepository::findByKeycloakId);
    }

    @Transactional
    @Override
    public void seedAppUser(UUID keycloakId, String preferredUsername) {
        if (appUserRepository.existsByKeycloakId(keycloakId)) {
            return;
        }

        try {
            createUser(keycloakId, preferredUsername);
        } catch (DataIntegrityViolationException e) {
            // Another request created the user in the meantime → safe to ignore
        }
    }

    private Optional<Map<String, Object>> getClaimsFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        if (!(authentication instanceof JwtAuthenticationToken jwtToken)) {
            return Optional.empty();
        }

        return Optional.of(jwtToken.getToken().getClaims());
    }

    private Optional<UUID> getUserIdFromContextHolder() {
        return getClaimsFromJwt()
                .map(claims -> UUID.fromString((String) claims.get("sub")));
    }

    private AppUser findAppUser(UUID keycloakId) {
        return appUserRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> {
                    log.warn("User not found with the id of {}.", keycloakId);
                    return new AppUserNotFoundException("User not found.");
                });
    }

    private void createUser(UUID keycloakId, String preferredUsername) {
        AppUser appUser = createAppUser(keycloakId, preferredUsername);
        appUserRepository.save(appUser);
    }
}
