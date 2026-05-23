package com.beatbox.beatboxbackend.listeningHistory;

import com.beatbox.beatboxbackend.listeningHistory.dto.ListeningHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tracks/history")
@RequiredArgsConstructor
public class ListeningHistoryController {

    private final ListeningHistoryService listeningHistoryService;

    @PostMapping("/{trackId}")
    public void recordPlay(@PathVariable UUID trackId) {
        listeningHistoryService.addToListeningHistory(trackId);
    }

    @GetMapping
    public Page<ListeningHistoryDto> getListeningHistory(
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "3") int pageSize
    ) {
        return listeningHistoryService.getListeningHistory(pageNum, pageSize);
    }

}
