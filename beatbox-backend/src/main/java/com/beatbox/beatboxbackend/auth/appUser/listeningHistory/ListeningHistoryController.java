package com.beatbox.beatboxbackend.auth.appUser.listeningHistory;

import com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto.ListeningHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/me/history")
@RequiredArgsConstructor
public class ListeningHistoryController {

    private final ListeningHistoryService listeningHistoryService;

    @PostMapping("/{trackId}")
    public ResponseEntity<Void> recordListeningHistory(@PathVariable UUID trackId) {
        listeningHistoryService.recordListeningHistory(trackId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Page<ListeningHistoryDto> getListeningHistory(
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "3") int pageSize
    ) {
        return listeningHistoryService.getListeningHistory(pageNum, pageSize);
    }

}
