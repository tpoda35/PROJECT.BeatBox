package com.beatbox.beatboxbackend.follow;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {

    Optional<Follow> findByFollowerAndFollowing(AppUser follower, AppUser following);

    @Modifying
    @Query("""
        DELETE FROM Follow f
        WHERE f.follower.id = :followerId
          AND f.following.id = :followingId
    """)
    int deleteByFollowerIdAndFollowingId(UUID followerId, UUID followingId);

}
