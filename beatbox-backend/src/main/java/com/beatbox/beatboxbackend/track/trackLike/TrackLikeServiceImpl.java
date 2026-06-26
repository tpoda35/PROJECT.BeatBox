package com.beatbox.beatboxbackend.track.trackLike;

import com.beatbox.beatboxbackend.auth.appUser.AppUser;
import com.beatbox.beatboxbackend.auth.appUser.AppUserService;
import com.beatbox.beatboxbackend.auth.exception.AuthException;
import com.beatbox.beatboxbackend.track.Track;
import com.beatbox.beatboxbackend.track.TrackRepository;
import com.beatbox.beatboxbackend.track.exception.TrackNotFoundException;
import com.beatbox.beatboxbackend.track.trackLike.dto.projection.TrackLikeCountPair;
import com.beatbox.beatboxbackend.track.trackLike.dto.TrackLikeDto;
import com.beatbox.beatboxbackend.track.trackLike.exception.TrackAlreadyLikedException;
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
public class TrackLikeServiceImpl implements TrackLikeService {

    private final AppUserService appUserService;
    private final TrackRepository trackRepository;
    private final TrackLikeRepository trackLikeRepository;

    @Transactional
    @Override
    public void likeTrack(UUID trackId) {
        AppUser user = appUserService.getLoggedInUser();

        Track track = findTrack(trackId);

        boolean alreadyLiked =
                trackLikeRepository.existsByUserAndTrack(user, track);

        if (alreadyLiked) {
            throw new TrackAlreadyLikedException();
        }

        TrackLike like = TrackLike.builder()
                .user(user)
                .track(track)
                .build();

        trackLikeRepository.save(like);
    }

    @Transactional
    @Override
    public void unlikeTrack(UUID trackId) {

        AppUser user = appUserService.getLoggedInUser();

        Track track = findTrack(trackId);

        trackLikeRepository.deleteByUserAndTrack(user, track);

    }

    @Override
    public Long getLikeCount(Track track) {
        return trackLikeRepository.countByTrack(track);
    }

    @Override
    public Page<TrackLikeDto> getLikedTracks(int pageNum, int pageSize) {
        AppUser loggedInUser = appUserService.getLoggedInUserOptional()
                .orElseThrow(AuthException::new);

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<TrackLikeCountPair> pairsPage = trackLikeRepository.findLikedTracksWithCount(
                loggedInUser,
                pageable
        );

        return pairsPage.map(pair ->
                TrackLikeMapper.createTrackLikeDto(
                        pair.trackLike(),
                        pair.likeCount(),
                        true
                )
        );
    }

    private Track findTrack(UUID trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(TrackNotFoundException::new);
    }
}
