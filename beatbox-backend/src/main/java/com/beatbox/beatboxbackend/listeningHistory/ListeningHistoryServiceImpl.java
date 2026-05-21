package com.beatbox.beatboxbackend.listeningHistory;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.AppUserService;
import com.beatbox.beatboxbackend.listeningHistory.dto.ListeningHistoryDto;
import com.beatbox.beatboxbackend.track.Track;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListeningHistoryServiceImpl implements ListeningHistoryService {

    private final ListeningHistoryRepository listeningHistoryRepository;
    private final AppUserService appUserService;

    @Transactional
    @Override
    public void addToListeningHistory(AppUser appUser, Track track) {
        ListeningHistory entry = ListeningHistory.builder()
                .user(appUser)
                .track(track)
                .build();

        listeningHistoryRepository.save(entry);
    }

    @Override
    public Page<ListeningHistoryDto> getListeningHistory(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "listenedAt"));

        AppUser loggedInUser = appUserService.getLoggedInUser();

        return listeningHistoryRepository
                .getAllByUser_Id(loggedInUser.getId(), pageable)
                .map(ListeningHistoryMapper::toListeningHistoryDto);
    }
}
