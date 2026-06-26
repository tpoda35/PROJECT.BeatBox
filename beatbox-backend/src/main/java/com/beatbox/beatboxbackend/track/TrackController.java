package com.beatbox.beatboxbackend.track;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @PostMapping
    public ResponseEntity<Void> uploadTrack(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        UUID newTrackId = trackService.uploadTrack(title, file).getId();
        URI location = URI.create("/api/tracks/" + newTrackId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{trackId}/stream")
    public ResponseEntity<ResourceRegion> streamTrack(
            @PathVariable UUID trackId,
            @RequestHeader HttpHeaders headers
    ) throws IOException {
        return trackService.streamTrack(trackId, headers);
    }

    @PostMapping("/{trackId}/views")
    public ResponseEntity<Void> recordView(@PathVariable UUID trackId) {
        trackService.recordView(trackId);
        return ResponseEntity.noContent().build();
    }
}
