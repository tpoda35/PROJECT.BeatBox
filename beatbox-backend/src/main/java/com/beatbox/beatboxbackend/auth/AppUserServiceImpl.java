package com.beatbox.beatboxbackend.auth;

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
    public AppUser getOrCreateAppUser(UUID keycloakId) {
        return appUserRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    try {
                        return createUser(keycloakId);
                    } catch (DataIntegrityViolationException e) {
                        // Race condition: another thread created it
                        return appUserRepository.findByKeycloakId(keycloakId)
                                .orElseThrow(() -> new IllegalStateException(
                                        "User creation failed unexpectedly"));
                    }
                });
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

    private AppUser createUser(UUID keycloakId) {
        AppUser appUser = createAppUser(keycloakId);
        return appUserRepository.save(appUser);
    }
}
