package com.beatbox.beatboxbackend.auth.appUser.listeningHistory;

import com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto.ListeningHistoryDto;
import com.beatbox.beatboxbackend.track.TrackMapper;

public class ListeningHistoryMapper {

    public static ListeningHistoryDto toListeningHistoryDto(ListeningHistory listeningHistory, Long likeCount, Boolean isLiked) {
        return new ListeningHistoryDto(
                TrackMapper.toTrackDto(listeningHistory.getTrack(), likeCount, isLiked),
                listeningHistory.getCreatedAt()
        );
    }

}
