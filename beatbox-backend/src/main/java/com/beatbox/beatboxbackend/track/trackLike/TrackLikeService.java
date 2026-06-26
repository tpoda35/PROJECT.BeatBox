package com.beatbox.beatboxbackend.track.trackLike;

import com.beatbox.beatboxbackend.track.Track;
import com.beatbox.beatboxbackend.track.trackLike.dto.TrackLikeDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TrackLikeService {
    void likeTrack(UUID trackId);
    void unlikeTrack(UUID trackId);
    Long getLikeCount(Track track);

    Page<TrackLikeDto> getLikedTracks(int pageNum, int pageSize);
}
