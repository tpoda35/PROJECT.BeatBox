package com.beatbox.beatboxbackend.track.trackLike;

import com.beatbox.beatboxbackend.track.TrackMapper;
import com.beatbox.beatboxbackend.track.trackLike.dto.TrackLikeDto;

public class TrackLikeMapper {

    public static TrackLikeDto createTrackLikeDto(TrackLike trackLike, Long likeCount, Boolean isLiked) {
        return new TrackLikeDto(
                TrackMapper.toTrackDto(trackLike.getTrack(), likeCount, isLiked),
                trackLike.getCreatedAt()
        );
    }

}
