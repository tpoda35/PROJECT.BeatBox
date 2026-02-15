import styles from "./Track.module.css";
import type {TrackProps} from "./TrackProps.ts";

const Track = ({ id, title, artist, coverUrl }: TrackProps) => {
    return (
        <div key={id} className={styles.card}>
            {/* Album Cover */}
            <div className={styles.coverWrapper}>
                <img
                    src={coverUrl}
                    alt={title}
                    className={styles.coverImage}
                />

                {/* Vertical Label */}
                <div className={styles.verticalLabel}>
                    RELATED TRACKS
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
