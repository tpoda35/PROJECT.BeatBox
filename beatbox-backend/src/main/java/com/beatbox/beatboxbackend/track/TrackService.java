package com.beatbox.beatboxbackend.track;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface TrackService {
    Track uploadTrack(String title, MultipartFile file) throws IOException;
    ResponseEntity<ResourceRegion> streamTrack(UUID trackId, HttpHeaders headers) throws IOException;
}
