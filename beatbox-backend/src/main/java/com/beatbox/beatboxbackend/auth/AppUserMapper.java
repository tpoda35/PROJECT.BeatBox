package com.beatbox.beatboxbackend.auth;

import java.util.UUID;

public class AppUserMapper {

    public static AppUser createAppUser(UUID keycloakId) {
        return AppUser.builder()
                .keycloakId(keycloakId)
                .build();
    }

}
