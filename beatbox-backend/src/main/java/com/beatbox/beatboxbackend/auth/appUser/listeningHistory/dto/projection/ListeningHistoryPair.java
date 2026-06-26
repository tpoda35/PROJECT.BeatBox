package com.beatbox.beatboxbackend.auth.appUser.listeningHistory.dto.projection;

import com.beatbox.beatboxbackend.auth.appUser.listeningHistory.ListeningHistory;

public record ListeningHistoryPair(
        ListeningHistory listeningHistory,
        long likeCount,
        boolean isLiked
) {}
