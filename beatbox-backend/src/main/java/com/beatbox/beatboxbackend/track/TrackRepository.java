package com.beatbox.beatboxbackend.track;

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
        SELECT t
        FROM Track t
        JOIN FETCH t.artists
    """)
    List<Track> findAllWithArtists();

    @Query("""
        SELECT t.id, COUNT(l)
        FROM Track t
        LEFT JOIN t.likes l
        GROUP BY t.id
    """)
    List<Object[]> findLikeCountsPerTrack();

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
}
