import styles from "./TrackListItem.module.css";
import type {TrackListItemProps} from "./TrackListItemProps.ts";
import {IconHeartFilled, IconMessageCircleFilled, IconPlayerPlayFilled, IconReload} from "@tabler/icons-react";

const TrackListItem = ({
                           artist,
                           title,
                           coverUrl,
                           plays,
                           likes,
                           reposts,
                           comments,
                       }: TrackListItemProps) => {
    const shortTitle = title.length > 25 ? title.slice(0, 25) + "..." : title;

    return (
        <div className={styles.trackListItem}>
            {/* Cover */}
            <img src={coverUrl} alt={title} className={styles.cover} />

            {/* Info */}
            <div className={styles.info}>
                <p className={styles.artist}>{artist}</p>

                <p className={styles.title}>{shortTitle}</p>

                {/* Stats */}
                <div className={styles.stats}>
                    <span><IconPlayerPlayFilled size={12} /> {plays.toLocaleString()}</span>
                    <span><IconHeartFilled size={12} /> {likes.toLocaleString()}</span>
                    <span><IconReload size={12} /> {reposts.toLocaleString()}</span>
                    <span><IconMessageCircleFilled size={12}/> {comments.toLocaleString()}</span>
                </div>
            </div>
        </div>
    );
};

export default TrackListItem;
