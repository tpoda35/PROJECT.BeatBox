package com.beatbox.beatboxbackend.auth;

import com.beatbox.beatboxbackend.track.Track;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user", indexes = {
        @Index(name = "idx_keycloak_id", columnList = "keycloakId")
})
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID keycloakId;

    @OneToMany(mappedBy = "artist")
    private List<Track> tracks;
}
