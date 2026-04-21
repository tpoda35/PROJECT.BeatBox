package com.beatbox.beatboxbackend.auth.appUser;

import java.util.UUID;

public interface AppUserService {
    AppUser getLoggedInUser();
    void seedAppUser(UUID keycloakId, String preferredUsername);
}
