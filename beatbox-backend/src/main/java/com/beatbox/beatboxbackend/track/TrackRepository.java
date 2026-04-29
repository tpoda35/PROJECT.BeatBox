package com.beatbox.beatboxbackend.track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrackRepository extends JpaRepository<Track, UUID> {

    @Query("""
        SELECT t
        FROM Track t
        JOIN FETCH t.artists
    """)
    List<Track> findAllWithArtists();

}
