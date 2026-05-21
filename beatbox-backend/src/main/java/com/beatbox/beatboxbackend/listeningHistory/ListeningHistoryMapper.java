package com.beatbox.beatboxbackend.listeningHistory;

import com.beatbox.beatboxbackend.listeningHistory.dto.ListeningHistoryDto;
import com.beatbox.beatboxbackend.track.TrackMapper;

public class ListeningHistoryMapper {

    public static ListeningHistoryDto toListeningHistoryDto(ListeningHistory listeningHistory) {
        return new ListeningHistoryDto(
                TrackMapper.toTrackDto(listeningHistory.getTrack()),
                listeningHistory.getListenedAt()
        );
    }

}
