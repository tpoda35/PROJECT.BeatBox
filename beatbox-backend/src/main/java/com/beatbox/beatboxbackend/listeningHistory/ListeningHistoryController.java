package com.beatbox.beatboxbackend.listeningHistory;

import com.beatbox.beatboxbackend.listeningHistory.dto.ListeningHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracks/history")
@RequiredArgsConstructor
public class ListeningHistoryController {

    private final ListeningHistoryService listeningHistoryService;

    @GetMapping
    public Page<ListeningHistoryDto> getListeningHistory(
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "3") int pageSize
    ) {
        return listeningHistoryService.getListeningHistory(pageNum, pageSize);
    }

}
