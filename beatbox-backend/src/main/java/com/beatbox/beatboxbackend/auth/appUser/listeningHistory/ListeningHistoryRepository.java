package com.beatbox.beatboxbackend.auth.appUser.listeningHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, UUID> {

    // track.artists should be eagerly loaded, but there was a duplicate problem
    @EntityGraph(attributePaths = {"track"})
    Page<ListeningHistory> getAllByUser_Id(UUID userId, Pageable pageable);

}
