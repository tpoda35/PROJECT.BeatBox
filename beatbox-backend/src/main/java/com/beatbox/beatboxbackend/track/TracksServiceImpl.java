package com.beatbox.beatboxbackend.track;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.AppUserService;
import com.beatbox.beatboxbackend.track.dto.TrackDto;
import com.beatbox.beatboxbackend.track.exception.TrackNotFoundException;
import com.beatbox.beatboxbackend.track.trackLike.TrackLikeRepository;
import com.beatbox.beatboxbackend.track.trackLike.TrackLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.beatbox.beatboxbackend.track.TrackMapper.createTrack;

@Service
@RequiredArgsConstructor
@Slf4j
public class TracksServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final AppUserService appUserService;
    private final TrackLikeRepository trackLikeRepository;

    private static final String CACHE_CONTROL_VALUE = "public, max-age=86400";

    @Value("${app.upload.audioDir}")
    private String audioUploadDir;

    @Transactional
    @Override
    public Track uploadTrack(String title, MultipartFile file) throws IOException {
        AppUser artist = appUserService.getLoggedInUser();

        // Resolve and validate MIME type at upload time, stored in DB,
        // so streamTrack() never needs to probe the disk
        String mimeType = file.getContentType();
        if (mimeType == null || mimeType.isBlank()) {
            mimeType = "application/octet-stream";
        }

        // Build a unique filename
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path target = Paths.get(audioUploadDir, fileName);
        Files.createDirectories(target.getParent());

        // Save metadata to DB first, if this fails, no orphaned file is written
        Track track = trackRepository.save(
                createTrack(title, fileName, mimeType, List.of(artist))
        );

        // Write file only after DB commit succeeds
        // Files.copy() works correctly both in dev and when running as a JAR
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Roll back the DB record if the file write fails
            trackRepository.delete(track);
            throw new IOException("Failed to write audio file: " + fileName, e);
        }

        return track;
    }

    @Override
    public ResponseEntity<ResourceRegion> streamTrack(UUID trackId, HttpHeaders headers)
            throws IOException {

        Track track = findTrack(trackId);

        // Resolve the physical file path on disk using the stored filename
        Path path = Paths.get(audioUploadDir, track.getFileName());

        // Ensure the file actually exists on disk
        // Prevents returning a broken stream for orphaned DB records
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found: " + track.getFileName());
        }

        // Wrap the file as a Spring Resource abstraction
        // UrlResource allows Spring to stream the file efficiently
        UrlResource resource = new UrlResource(path.toUri());

        // Read total file size, needed for range requests and caching headers
        long contentLength = resource.contentLength();

        // Resolve MIME type for the response Content-Type header
        // Uses DB value first, falls back to probing the filesystem
        String mimeType = resolveMediaType(track, path);

        // Build cache validation metadata
        // ETag + Last-Modified allow browsers to avoid re-downloading unchanged files
        FileTime lastModifiedTime = Files.getLastModifiedTime(path);
        long lastModifiedMillis = lastModifiedTime.toMillis();

        // Example:
        // "5829384-1715780000000"
        //
        // Combines:
        // - file size
        // - last modified timestamp
        //
        // If either changes, the ETag changes too
        String eTag = "\"" + contentLength + "-" + lastModifiedMillis + "\"";

        // HTTP Last-Modified headers operate at second precision
        // so truncate nanoseconds for consistent comparisons
        Instant lastModifiedInstant = lastModifiedTime.toInstant()
                .truncatedTo(ChronoUnit.SECONDS);

        // Browser cache validation using ETag
        //
        // Browser sends:
        // If-None-Match: "5829384-1715780000000"
        //
        // If the file has not changed:
        // -> return 304 Not Modified
        // -> browser reuses its cached copy
        //
        // This avoids re-streaming the whole audio file
        String ifNoneMatch = headers.getFirst(HttpHeaders.IF_NONE_MATCH);
        if (eTag.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .header(HttpHeaders.ETAG, eTag)
                    .header(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_VALUE)
                    .lastModified(lastModifiedInstant)
                    .build();
        }

        // Fallback cache validation using Last-Modified date
        //
        // Some clients use If-Modified-Since instead of ETag
        //
        // If the browser already has the latest version:
        // -> return 304 Not Modified
        long ifModifiedSince = headers.getIfModifiedSince();
        if (ifModifiedSince != -1 && lastModifiedMillis <= ifModifiedSince) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .header(HttpHeaders.ETAG, eTag)
                    .header(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_VALUE)
                    .lastModified(lastModifiedInstant)
                    .build();
        }

        // Read HTTP Range headers
        //
        // Range requests allow clients to request only part of a file:
        //
        // Example:
        // Range: bytes=1000-5000
        //
        // Important for:
        // - seeking inside audio/video
        // - resumable downloads
        // - CDNs
        List<HttpRange> ranges = headers.getRange();

        ResourceRegion region;
        HttpStatus status;

        if (ranges.isEmpty()) {
            // No range request:
            // return the entire file with HTTP 200
            //
            // Axios usually downloads the whole file at once
            region = new ResourceRegion(resource, 0, contentLength);
            status = HttpStatus.OK;
        } else {
            // Partial content request:
            // return only the requested byte range
            //
            // Example:
            // bytes 1000-5000/5829384
            //
            // Native <audio> elements commonly use this
            region = ranges.getFirst().toResourceRegion(resource);
            status = HttpStatus.PARTIAL_CONTENT;
        }

        // Build the HTTP response headers
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(status)
                // Content-Type:
                // audio/mpeg, audio/wav, etc.
                .contentType(MediaType.parseMediaType(mimeType))

                // Tell clients range requests are supported
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")

                // Browser/CDN caching instructions
                .header(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_VALUE)

                // Cache validation identifier
                .header(HttpHeaders.ETAG, eTag)

                // "inline" tells browsers to play/display instead of downloading
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + track.getFileName() + "\"")

                // Last modification timestamp
                .lastModified(lastModifiedInstant)

                // Size of THIS response region
                //
                // Full file:
                // -> total size
                //
                // Partial content:
                // -> requested range size
                .contentLength(region.getCount());

        // Content-Range is ONLY valid for partial responses
        //
        // Example:
        // Content-Range: bytes 1000-5000/5829384
        //
        // Meaning:
        // "This response contains bytes 1000-5000
        //  from a total file size of 5829384"
        if (status == HttpStatus.PARTIAL_CONTENT) {
            builder = builder.header(HttpHeaders.CONTENT_RANGE,
                    "bytes " + region.getPosition() + "-"
                            + (region.getPosition() + region.getCount() - 1)
                            + "/" + contentLength);
        }

        // Return the streaming response body
        //
        // Spring streams the ResourceRegion efficiently
        // without loading the whole file into memory
        return builder.body(region);
    }

    private String resolveMediaType(Track track, Path path) throws IOException {
        // Prefer MIME type already stored in DB at upload time
        // Avoids repeatedly probing the filesystem on every stream request
        if (track.getMimeType() != null && !track.getMimeType().isBlank()) {
            return track.getMimeType();
        }

        // Fallback: ask the operating system / filesystem to detect type like audio/mpeg or audio/wav
        String probed = Files.probeContentType(path);

        // Final fallback for unknown binary files
        return (probed != null) ? probed : "application/octet-stream"; // == "generic binary data"
    }

    // Optimize it: pagination + add recommendation algorithm + cache
    @Override
    public List<TrackDto> getTracks() {
        List<Track> tracks = trackRepository.findAllWithArtists();

        Map<UUID, Long> likeCountById = trackRepository.findLikeCountsPerTrack()
                .stream()
                .collect(Collectors.toMap(
                        row -> (UUID) row[0],
                        row -> (Long) row[1]
                ));

        Optional<AppUser> loggedInUser = appUserService.getLoggedInUserOptional();

        if (loggedInUser.isPresent()) {
            Set<UUID> trackIds = tracks.stream()
                    .map(Track::getId)
                    .collect(Collectors.toSet());

            Set<UUID> likedTrackIds = trackLikeRepository
                    .findLikedTrackIdsByUserAndTrackIds(loggedInUser.get(), trackIds);

            return tracks.stream()
                    .map(track -> TrackMapper.toTrackDto(
                            track,
                            likeCountById.getOrDefault(track.getId(), 0L),
                            likedTrackIds.contains(track.getId())
                    ))
                    .toList();
        }

        return tracks.stream()
                .map(track -> TrackMapper.toTrackDto(
                        track,
                        likeCountById.getOrDefault(track.getId(), 0L)
                ))
                .toList();
    }

    private Track findTrack(UUID trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(TrackNotFoundException::new);
    }
}
