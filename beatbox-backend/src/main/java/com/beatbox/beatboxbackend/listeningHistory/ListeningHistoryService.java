package com.beatbox.beatboxbackend.listeningHistory;

import com.beatbox.beatboxbackend.listeningHistory.dto.ListeningHistoryDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ListeningHistoryService {
    void addToListeningHistory(UUID trackId);
    Page<ListeningHistoryDto> getListeningHistory(int pageNum, int pageSize);
}
