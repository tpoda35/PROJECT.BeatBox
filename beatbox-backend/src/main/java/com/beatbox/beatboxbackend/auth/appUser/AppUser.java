package com.beatbox.beatboxbackend.auth.appUser;

import com.beatbox.beatboxbackend.follow.Follow;
import com.beatbox.beatboxbackend.listeningHistory.ListeningHistory;
import com.beatbox.beatboxbackend.track.Track;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_users", indexes = {
        @Index(name = "idx_keycloak_id", columnList = "keycloakId")
})
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID keycloakId;

    private String preferredUsername;

    @Column(nullable = false)
    private boolean isVerified = false;

    // Uploaded tracks
    @ManyToMany(mappedBy = "artists")
    private List<Track> tracks = new ArrayList<>();

    // Follow system
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> following = new ArrayList<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>();

    // History
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListeningHistory> listeningHistory;

    // Likes

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    @Version
    private Long version;
}
