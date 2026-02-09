package com.beatbox.beatboxbackend.track;

import com.beatbox.beatboxbackend.auth.AppUser;

public class TrackMapper {

    public static Track createTrack(String title, String fileName, AppUser artist) {
        return Track.builder()
                .title(title)
                .fileName(fileName)
                .artist(artist)
                .build();
    }

}
