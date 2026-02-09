package com.beatbox.beatboxbackend.track;

import com.beatbox.beatboxbackend.auth.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Title cannot be blank.")
    @Size(min = 1, max = 120, message = "Title must be between 1 and 120 characters.")
    private String title;

    @NotBlank(message = "File name cannot be blank.")
    @Size(max = 200, message = "File name cannot exceed 200 characters.")
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    @NotNull(message = "Artist cannot be null.")
    private AppUser artist;

//    @NotNull(message = "Duration is required.")
//    @Min(value = 1, message = "Duration must be at least 1 second.")
//    @Max(value = 3600, message = "Duration cannot exceed 1 hour.")
//    private Integer duration;

    @Column(updatable = false)
    @CreationTimestamp
    private OffsetDateTime uploadedAt;

    //Stats later
}
