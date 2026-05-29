package com.beatbox.beatboxbackend.auth.appUser.listeningHistory;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.AppUserService;
import com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto.ListeningHistoryDto;
import com.beatbox.beatboxbackend.track.Track;
import com.beatbox.beatboxbackend.track.TrackMapper;
import com.beatbox.beatboxbackend.track.TrackRepository;
import com.beatbox.beatboxbackend.track.exception.TrackNotFoundException;
import com.beatbox.beatboxbackend.track.trackLike.TrackLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListeningHistoryServiceImpl implements ListeningHistoryService {

    private final ListeningHistoryRepository listeningHistoryRepository;
    private final AppUserService appUserService;
    private final TrackRepository trackRepository;
    private final TrackLikeRepository trackLikeRepository;

    @Transactional
    @Override
    public void addToListeningHistory(UUID trackId) {
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

    @Transactional
    @Override
    public Page<ListeningHistoryDto> getListeningHistory(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        AppUser loggedInUser = appUserService.getLoggedInUser();

        Page<ListeningHistory> page = listeningHistoryRepository
                .getAllByUser_Id(loggedInUser.getId(), pageable);

        Map<UUID, Long> likeCountById = listeningHistoryRepository
                .findLikeCountsByUserId(loggedInUser.getId())
                .stream()
                .collect(Collectors.toMap(
                        row -> (UUID) row[0],
                        row -> (Long) row[1]
                ));

        Set<UUID> pageTrackIds = page.getContent().stream()
                .map(lh -> lh.getTrack().getId())
                .collect(Collectors.toSet());

        Set<UUID> likedTrackIds = trackLikeRepository
                .findLikedTrackIdsByUserAndTrackIds(loggedInUser, pageTrackIds);

        return page.map(listeningHistory -> ListeningHistoryMapper.toListeningHistoryDto(
                listeningHistory,
                likeCountById.getOrDefault(listeningHistory.getTrack().getId(), 0L),
                likedTrackIds.contains(listeningHistory.getTrack().getId())
        ));
    }
}
