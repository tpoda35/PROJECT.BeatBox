package com.beatbox.beatboxbackend.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppUserSeedingFilter extends OncePerRequestFilter {

    private final AppUserService appUserService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        // Only process if user is authenticated and it's a JWT token
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken) &&
                authentication.getPrincipal() instanceof Jwt jwt) {

            String keycloakIdStr = jwt.getSubject();

            try {
                UUID keycloakId = UUID.fromString(keycloakIdStr);
                appUserService.getOrCreateAppUser(keycloakId);

                log.debug("User seeded/verified for keycloakId: {}", keycloakId);

            } catch (IllegalArgumentException e) {
                log.error("Invalid Keycloak ID format: {}", keycloakIdStr, e);
            } catch (Exception e) {
                log.error("Failed to seed user for keycloakId: {}", keycloakIdStr, e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
