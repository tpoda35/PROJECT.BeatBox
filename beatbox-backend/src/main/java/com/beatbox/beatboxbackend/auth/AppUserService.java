package com.beatbox.beatboxbackend.auth;

import java.util.UUID;

public interface AppUserService {
    AppUser getAppUser(UUID id);
    AppUser getOrCreateAppUser(UUID keycloakId);
}
