package com.beatbox.beatboxbackend.listeningHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, UUID> {

    @EntityGraph(attributePaths = {"track", "track.artists"})
    Page<ListeningHistory> getAllByUser_Id(UUID userId, Pageable pageable);

}
