package com.beatbox.beatboxbackend.auth.appUser.listeningHistory;

import com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto.ListeningHistoryDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ListeningHistoryService {
    void recordListeningHistory(UUID trackId);
    Page<ListeningHistoryDto> getListeningHistory(int pageNum, int pageSize);
}
