package com.beatbox.beatboxbackend.auth.appUser;

import com.beatbox.beatboxbackend.auth.appUser.dto.ArtistDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByKeycloakId(UUID keycloakId);

    boolean existsByKeycloakId(UUID keycloakId);

    @Query("""
        SELECT new com.beatbox.beatboxbackend.auth.appUser.dto.ArtistDto(
            au.id,
            au.preferredUsername,
            COUNT(DISTINCT f.id),
            COUNT(DISTINCT t.id),
            au.isVerified,
            CASE WHEN :currentUserId IS NOT NULL AND EXISTS (
                SELECT fl FROM Follow fl
                WHERE fl.follower.id = :currentUserId
                AND fl.following.id = au.id
            ) THEN true ELSE false END
        )
        FROM AppUser au
        LEFT JOIN au.followers f
        LEFT JOIN au.tracks t
        WHERE au.id IN :ids
        GROUP BY au.id, au.preferredUsername, au.isVerified
    """)
    List<ArtistDto> findArtistsWithStatsByIds(List<UUID> ids, UUID currentUserId);
}
