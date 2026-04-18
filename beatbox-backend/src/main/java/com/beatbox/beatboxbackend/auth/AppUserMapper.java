package com.beatbox.beatboxbackend.auth;

import java.util.UUID;

public class AppUserMapper {

    public static AppUser createAppUser(UUID keycloakId, String preferredUsername) {
        return AppUser.builder()
                .preferredUsername(preferredUsername)
                .keycloakId(keycloakId)
                .build();
    }
}
