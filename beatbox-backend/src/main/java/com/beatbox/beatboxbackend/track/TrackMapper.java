package com.beatbox.beatboxbackend.track;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.track.dto.TrackDto;

import java.util.List;

public class TrackMapper {

    public static Track createTrack(String title, String fileName, String mimeType, List<AppUser> artists) {
        return Track.builder()
                .title(title)
                .fileName(fileName)
                .mimeType(mimeType)
                .artists(artists)
                .build();
    }

    public static TrackDto toTrackDto(Track track, Long likeCount) {
        return new TrackDto(
                track.getId(),
                track.getTitle(),
                track.getArtists()
                        .stream()
                        .map(AppUser::getPreferredUsername)
                        .toList(),
                likeCount,
                false,
                track.getViews()
        );
    }

    public static TrackDto toTrackDto(Track track, Long likeCount, Boolean isLiked) {
        return new TrackDto(
                track.getId(),
                track.getTitle(),
                track.getArtists()
                        .stream()
                        .map(AppUser::getPreferredUsername)
                        .toList(),
                likeCount,
                isLiked,
                track.getViews()
        );
    }
}
