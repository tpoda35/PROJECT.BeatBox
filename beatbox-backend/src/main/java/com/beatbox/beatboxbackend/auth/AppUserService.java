package com.beatbox.beatboxbackend.auth;

import java.util.UUID;

public interface AppUserService {
    AppUser getLoggedInUser();
    AppUser getOrCreateAppUser(UUID keycloakId);
}
