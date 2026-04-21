package com.beatbox.beatboxbackend.track;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.AppUserService;
import com.beatbox.beatboxbackend.track.exception.TrackNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.beatbox.beatboxbackend.track.TrackMapper.createTrack;

@Service
@RequiredArgsConstructor
@Slf4j
public class TracksServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final AppUserService appUserService;

    @Value("${app.upload.audioDir}")
    private String audioUploadDir;

    @Transactional
    @Override
    public Track uploadTrack(String title, MultipartFile file) throws IOException {
        AppUser artist = appUserService.getLoggedInUser();
        Track track = null;
        boolean saved = false;

        while (!saved) {
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path target = Paths.get(audioUploadDir, fileName);
            Files.createDirectories(target.getParent());

            file.transferTo(target.toFile());

            try {
                track = trackRepository.save(createTrack(title, fileName, artist));
                saved = true;
            } catch (DataIntegrityViolationException e) {
                Files.deleteIfExists(target);
            }
        }

        return track;
    }

    @Override
    public ResponseEntity<ResourceRegion> streamTrack(UUID trackId, HttpHeaders headers) throws IOException {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(TrackNotFoundException::new);

        Path path = Paths.get(audioUploadDir, track.getFileName());
        UrlResource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new FileNotFoundException("File not found: " + track.getFileName());
        }

        long contentLength = resource.contentLength();

        String mimeType = Files.probeContentType(path);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        String eTag = generateEtag(path);

        String ifNoneMatch = headers.getFirst(HttpHeaders.IF_NONE_MATCH);
        if (eTag.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .header(HttpHeaders.ETAG, eTag)
                    .header(HttpHeaders.CACHE_CONTROL, "public, max-age=86400")
                    .build();
        }

        List<HttpRange> ranges = headers.getRange();

        ResponseEntity.BodyBuilder builder = ranges.isEmpty()
                ? ResponseEntity.ok()
                : ResponseEntity.status(HttpStatus.PARTIAL_CONTENT);

        ResourceRegion region;

        if (ranges.isEmpty()) {
            region = new ResourceRegion(resource, 0, contentLength);
        } else {
            HttpRange range = ranges.getFirst();
            region = range.toResourceRegion(resource);
        }

        return builder
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=86400")
                .header(HttpHeaders.ETAG, eTag)
                .contentLength(region.getCount())
                .body(region);
    }

    private String generateEtag(Path path) throws IOException {
        long lastModified = Files.getLastModifiedTime(path).toMillis();
        long size = Files.size(path);
        return "\"" + lastModified + "-" + size + "\"";
    }

}
