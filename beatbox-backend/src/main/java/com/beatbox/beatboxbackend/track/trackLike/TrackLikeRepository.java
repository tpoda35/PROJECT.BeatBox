package com.beatbox.beatboxbackend.track.trackLike;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.track.Track;
import com.beatbox.beatboxbackend.track.trackLike.dto.projection.TrackLikeCountPair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface TrackLikeRepository extends JpaRepository<TrackLike, UUID> {

    boolean existsByUserAndTrack(AppUser user, Track track);

    void deleteByUserAndTrack(AppUser user, Track track);

    long countByTrack(Track track);

    @Query("SELECT l.track.id FROM TrackLike l WHERE l.user = :user AND l.track.id IN :trackIds")
    Set<UUID> findLikedTrackIdsByUserAndTrackIds(AppUser user, Set<UUID> trackIds);

    @Query("SELECT new com.beatbox.beatboxbackend.track.trackLike.dto.projection.TrackLikeCountPair(tl, " +
            "(SELECT COUNT(l) FROM TrackLike l WHERE l.track = t)) " +
            "FROM TrackLike tl " +
            "JOIN tl.track t " +
            "JOIN t.artists " +
            "WHERE tl.user = :user")
    Page<TrackLikeCountPair> findLikedTracksWithCount(AppUser user, Pageable pageable);
}
