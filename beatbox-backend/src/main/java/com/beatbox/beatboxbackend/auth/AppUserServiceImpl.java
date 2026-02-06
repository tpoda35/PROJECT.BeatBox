package com.beatbox.beatboxbackend.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.beatbox.beatboxbackend.auth.AppUserMapper.createAppUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public AppUser getAppUser(UUID id) {
        return null;
    }

    @Override
    @Transactional
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

    private AppUser createUser(UUID keycloakId) {
        AppUser appUser = createAppUser(keycloakId);
        return appUserRepository.save(appUser);
    }
}
