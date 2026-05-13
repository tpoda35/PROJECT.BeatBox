import styles from "./Track.module.css";
import type {TrackProps} from "./types/TrackProps.ts";
import {useTrack} from "./TrackContext.tsx";

const Track = ({ trackId, title, artist, coverUrl }: TrackProps) => {
    const { selectedTrackId, setSelectedTrackId } = useTrack();
    const isActive = selectedTrackId === trackId;

    return (
        <div
            className={`${styles.card} ${isActive ? styles.active : ""}`}
            onClick={() => setSelectedTrackId(trackId)}
            role="button"
            onKeyDown={(e) => e.key === "Enter" && setSelectedTrackId(trackId)}
            aria-pressed={isActive}
        >
            {/* Album Cover */}
            <div className={styles.coverWrapper}>
                <img
                    src={coverUrl}
                    alt={title}
                    className={styles.coverImage}
                />

                {/* Play overlay shown on hover or when active */}
                <div className={styles.playOverlay}>
                    <svg viewBox="0 0 24 24" fill="currentColor" className={styles.playIcon}>
                        <path d="M8 5v14l11-7z" />
                    </svg>
                </div>
            </div>

            {/* Info */}
            <div className={styles.info}>
                <div className={styles.title}>{title}</div>
                <div className={styles.artist}>{artist}</div>
            </div>
        </div>
    );
};

export default Track;