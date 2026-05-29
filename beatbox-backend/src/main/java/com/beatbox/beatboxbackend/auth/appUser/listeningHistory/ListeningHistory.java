package com.beatbox.beatboxbackend.auth.appUser.listeningHistory;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.track.Track;
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
@Table(name = "listeningHistories")
public class ListeningHistory {

    // This is for storing the history of a user.
    // There may be better solutions, but this one does the next:
    // If a user plays a sound, then a new db entry created.
    // These entries are duplicated, only the createdAt changes.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Track track;

    @CreationTimestamp
    private Instant createdAt;

}
