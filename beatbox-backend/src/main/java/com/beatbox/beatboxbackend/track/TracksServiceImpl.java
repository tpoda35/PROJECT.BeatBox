package com.beatbox.beatboxbackend.track;

import com.beatbox.beatboxbackend.auth.AppUser;
import com.beatbox.beatboxbackend.auth.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.beatbox.beatboxbackend.track.TrackMapper.createTrack;

@Service
@RequiredArgsConstructor
@Slf4j
public class TracksServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final AppUserService appUserService;

    @Transactional
    @Override
    public Track uploadTrack(String title, MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path target = Paths.get("uploads/audio/" + fileName);
        Files.createDirectories(target.getParent());
        Files.write(target, file.getBytes());

        AppUser artist = appUserService.getLoggedInUser();

        return trackRepository.save(createTrack(title, fileName, artist));
    }

    @Override
    public ResponseEntity<ResourceRegion> streamTrack(UUID trackId, HttpHeaders headers) throws IOException {
        Track track = trackRepository.findById(trackId).orElseThrow();
        Path path = Paths.get("uploads/audio/" + track.getFileName());
        UrlResource resource = new UrlResource(path.toUri());

        long contentLength = Files.size(path);

        String mimeType = Files.probeContentType(path);
        if (mimeType == null) mimeType = "application/octet-stream";

        HttpRange range = headers.getRange().isEmpty() ? null : headers.getRange().getFirst();

        long start = 0;
        long end = contentLength - 1;

        if (range != null) {
            start = range.getRangeStart(contentLength);
            end = range.getRangeEnd(contentLength);
        }

//        long chunkSize = 1024 * 1024;
//        long rangeLength = Math.min(chunkSize, end - start + 1);

        long rangeLength = end - start + 1;

        ResourceRegion region = new ResourceRegion(resource, start, rangeLength);

        return ResponseEntity.status(range == null ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.parseMediaType(mimeType))
                .header("Accept-Ranges", "bytes")
                .header("Content-Range", "bytes " + start + "-" + (start + rangeLength - 1) + "/" + contentLength)
                .contentLength(rangeLength)
                .body(region);
    }

}
