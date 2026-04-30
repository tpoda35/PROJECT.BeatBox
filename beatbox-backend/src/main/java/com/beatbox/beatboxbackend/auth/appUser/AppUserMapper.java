package com.beatbox.beatboxbackend.auth.appUser;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.UUID;

public class AppUserMapper {

    public static AppUser createAppUser(UUID keycloakId, String preferredUsername) {
        return AppUser.builder()
                .preferredUsername(preferredUsername)
                .keycloakId(keycloakId)
                .build();
    }

    public static AppUser fromKeycloakUserToAppUser(UserRepresentation userRepresentation) {
        return AppUser.builder()
                .keycloakId(UUID.fromString(userRepresentation.getId()))
                .preferredUsername(userRepresentation.getUsername())
                .build();
    }
}
