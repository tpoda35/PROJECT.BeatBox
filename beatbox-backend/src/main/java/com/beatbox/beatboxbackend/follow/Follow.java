package com.beatbox.beatboxbackend.follow;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"}) // A user cannot follow another user 2 times or more
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Who follows
    @ManyToOne(optional = false)
    @JoinColumn(name = "follower_id")
    private AppUser follower;

    // Who is being followed
    @ManyToOne(optional = false)
    @JoinColumn(name = "following_id")
    private AppUser following;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant followedAt;

}
