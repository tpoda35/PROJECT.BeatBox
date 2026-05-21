package com.beatbox.beatboxbackend.listeningHistory;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.listeningHistory.dto.ListeningHistoryDto;
import com.beatbox.beatboxbackend.track.Track;
import org.springframework.data.domain.Page;

public interface ListeningHistoryService {
    void addToListeningHistory(AppUser appUser, Track track);
    Page<ListeningHistoryDto> getListeningHistory(int pageNum, int pageSize);
}
