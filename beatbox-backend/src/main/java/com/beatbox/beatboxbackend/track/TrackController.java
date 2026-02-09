package com.beatbox.beatboxbackend.track;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;
    private final TrackRepository trackRepository;

    @PostMapping("/api/tracks/upload")
    public void uploadTrack(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        trackService.uploadTrack(title, file);
    }

    @GetMapping("/api/tracks/stream/{trackId}")
    public ResponseEntity<ResourceRegion> streamTrack(
            @PathVariable UUID trackId,
            @RequestHeader HttpHeaders headers
    ) throws IOException {
        return trackService.streamTrack(trackId, headers);
    }
}
