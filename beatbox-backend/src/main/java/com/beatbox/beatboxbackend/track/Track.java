package com.beatbox.beatboxbackend.track;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Title cannot be blank.")
    @Size(min = 1, max = 120, message = "Title must be between 1 and 120 characters.")
    private String title;

    @NotBlank(message = "File name cannot be blank.")
    @Size(max = 200, message = "File name cannot exceed 200 characters.")
    @Column(unique = true)
    private String fileName;

    @ManyToMany
    @JoinTable(
            name = "track_artists",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<AppUser> artists;

    @Column(length = 127)
    private String mimeType;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant uploadedAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    @Version
    private Long version;

    public void addArtist(AppUser artist) {
        this.artists.add(artist);
        artist.getTracks().add(this);
    }

    public void removeArtist(AppUser artist) {
        this.artists.remove(artist);
        artist.getTracks().remove(this);
    }
}
