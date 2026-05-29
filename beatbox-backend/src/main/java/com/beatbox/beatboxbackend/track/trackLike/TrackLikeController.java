package com.beatbox.beatboxbackend.track.trackLike;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tracks/{trackId}/like")
@RequiredArgsConstructor
public class TrackLikeController {

    private final TrackLikeService trackLikeService;

    @PostMapping
    public ResponseEntity<Void> likeTrack(@PathVariable UUID trackId) {

        trackLikeService.likeTrack(trackId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikeTrack(@PathVariable UUID trackId) {

        trackLikeService.unlikeTrack(trackId);

        return ResponseEntity.noContent().build();
    }

}
