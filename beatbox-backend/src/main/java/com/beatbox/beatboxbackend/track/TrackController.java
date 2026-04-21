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
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @PostMapping("/upload")
    public void uploadTrack(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        trackService.uploadTrack(title, file);
    }

    @GetMapping("/stream/{trackId}")
    public ResponseEntity<ResourceRegion> streamTrack(
            @PathVariable UUID trackId,
            @RequestHeader HttpHeaders headers
    ) throws IOException {
        return trackService.streamTrack(trackId, headers);
    }
}
