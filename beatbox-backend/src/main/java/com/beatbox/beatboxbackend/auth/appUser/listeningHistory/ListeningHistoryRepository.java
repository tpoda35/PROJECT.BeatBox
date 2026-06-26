package com.beatbox.beatboxbackend.auth.appUser.listeningHistory;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto.projection.ListeningHistoryPair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, UUID> {

    @Query("""
        SELECT new com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto.projection.ListeningHistoryPair(
            lh,
            (SELECT COUNT(l) FROM TrackLike l WHERE l.track = lh.track),
            (CASE WHEN (SELECT COUNT(ul) FROM TrackLike ul WHERE ul.track = lh.track AND ul.user = :user) > 0 THEN true ELSE false END)
        )
        FROM ListeningHistory lh
        JOIN FETCH lh.track t
        WHERE lh.user = :user
    """)
    Page<ListeningHistoryPair> findHistoryWithMetricsByUser(AppUser user, Pageable pageable);

}
