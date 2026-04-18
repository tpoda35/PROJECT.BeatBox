package com.beatbox.beatboxbackend.auth;

import com.beatbox.beatboxbackend.auth.dto.RecommendedArtistsDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByKeycloakId(UUID keycloakId);

    @Query("""
        SELECT new com.beatbox.beatboxbackend.auth.dto.RecommendedArtistsDto(
            au.id,
            au.preferredUsername,
            COUNT(DISTINCT f.id),
            COUNT(DISTINCT t.id)
        )
        FROM AppUser au
        LEFT JOIN au.followers f
        LEFT JOIN au.tracks t
        WHERE au.id IN :ids
        GROUP BY au.id, au.preferredUsername
    """)
    List<RecommendedArtistsDto> findArtistsWithStatsByIds(List<UUID> ids);
}
