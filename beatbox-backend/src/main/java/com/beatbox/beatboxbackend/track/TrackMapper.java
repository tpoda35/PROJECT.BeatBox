package com.beatbox.beatboxbackend.track;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;

import java.util.List;

public class TrackMapper {

    public static Track createTrack(String title, String fileName, List<AppUser> artists) {
        return Track.builder()
                .title(title)
                .fileName(fileName)
                .artists(artists)
                .build();
    }

}
