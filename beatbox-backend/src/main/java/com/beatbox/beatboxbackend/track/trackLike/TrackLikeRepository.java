package com.beatbox.beatboxbackend.track.trackLike;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.track.Track;
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

    @Query("SELECT l.track.id FROM TrackLike l WHERE l.user = :user")
    Set<UUID> findLikedTrackIdsByUser(AppUser user);

    @Query("SELECT l.track.id FROM TrackLike l WHERE l.user = :user AND l.track.id IN :trackIds")
    Set<UUID> findLikedTrackIdsByUserAndTrackIds(AppUser user, Set<UUID> trackIds);
}
