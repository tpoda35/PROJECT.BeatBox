package com.beatbox.beatboxbackend.auth.appUser.listeningHistory;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.AppUserService;
import com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto.ListeningHistoryDto;
import com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto.projection.ListeningHistoryPair;
import com.beatbox.beatboxbackend.track.Track;
import com.beatbox.beatboxbackend.track.TrackRepository;
import com.beatbox.beatboxbackend.track.exception.TrackNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListeningHistoryServiceImpl implements ListeningHistoryService {

    private final ListeningHistoryRepository listeningHistoryRepository;
    private final AppUserService appUserService;
    private final TrackRepository trackRepository;

    @Transactional
    @Override
    public void recordListeningHistory(UUID trackId) {
        AppUser appUser = appUserService.getLoggedInUserOptional()
                .orElse(null);

        if (appUser != null) {
            Track track = trackRepository.findById(trackId)
                    .orElseThrow(TrackNotFoundException::new);

            ListeningHistory entry = ListeningHistory.builder()
                    .user(appUser)
                    .track(track)
                    .build();

            listeningHistoryRepository.save(entry);
        }
    }

    @Override
    public Page<ListeningHistoryDto> getListeningHistory(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        AppUser loggedInUser = appUserService.getLoggedInUser();

        Page<ListeningHistoryPair> historyPage = listeningHistoryRepository
                .findHistoryWithMetricsByUser(loggedInUser, pageable);

        return historyPage.map(pair -> ListeningHistoryMapper.toListeningHistoryDto(
                pair.listeningHistory(),
                pair.likeCount(),
                pair.isLiked()
        ));
    }
}
