package com.beatbox.beatboxbackend.track;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.track.dto.projection.TrackRecommendationPair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TrackRepository extends JpaRepository<Track, UUID> {

    @Query("""
        SELECT t.id, COUNT(l)
        FROM Track t
        LEFT JOIN t.likes l
        WHERE t.id IN :trackIds
        GROUP BY t.id
    """)
    List<Object[]> findLikeCountsByTrackIds(Set<UUID> trackIds);

    @Modifying
    @Query("UPDATE Track t SET t.views = t.views + 1 WHERE t.id = :trackId")
    void incrementViews(UUID trackId);

    @Query("""
        SELECT new com.beatbox.beatboxbackend.track.dto.projection.TrackRecommendationPair(
            t,
            (SELECT COUNT(l) FROM TrackLike l WHERE l.track = t),
            (CASE WHEN (SELECT COUNT(ul) FROM TrackLike ul WHERE ul.track = t AND ul.user = :user) > 0 THEN true ELSE false END)
        )
        FROM Track t
        JOIN t.artists
    """)
    Page<TrackRecommendationPair> findRecommendationsForUser(AppUser user, Pageable pageable);

    @Query("""
        SELECT new com.beatbox.beatboxbackend.track.dto.projection.TrackRecommendationPair(
            t,
            (SELECT COUNT(l) FROM TrackLike l WHERE l.track = t),
            false
        )
        FROM Track t
        JOIN t.artists
    """)
    Page<TrackRecommendationPair> findRecommendationsAnonymous(Pageable pageable);
}
