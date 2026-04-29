package com.beatbox.beatboxbackend.auth.appUser;

import java.util.Optional;
import java.util.UUID;

public interface AppUserService {
    AppUser getLoggedInUser();
    Optional<AppUser> getLoggedInUserOptional();
    void seedAppUser(UUID keycloakId, String preferredUsername);
}
