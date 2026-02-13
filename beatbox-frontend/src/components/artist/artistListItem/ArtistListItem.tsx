import type {ArtistListItemProps} from "./ArtistListItemProps.ts";
import styles from "./ArtistListItem.module.css";
import {IconCircleCheckFilled, IconMusic, IconUserFilled} from "@tabler/icons-react";

const ArtistListItem = ({
                            name,
                            followers,
                            songs,
                            imageUrl,
                            verified = false,
                            onFollow,
                        }: ArtistListItemProps) => {
    const displayName = name.length > 8 ? name.slice(0, 8) + "..." : name;

    return (
        <div className={styles.container}>
            {/* Left side */}
            <div className={styles.left}>
                <img src={imageUrl} alt={name} className={styles.avatar} />

                <div>
                    <div className={styles.nameRow}>
                        <h4 className={styles.name}>{displayName}</h4>
                        {verified && <span className={styles.verified}><IconCircleCheckFilled size={20} /></span>}
                    </div>

                    <div className={styles.meta}>
                        <IconUserFilled size={12} /> {followers.toLocaleString()} &nbsp; <IconMusic size={12} /> {songs}
                    </div>
                </div>
            </div>

            {/* Right side */}
            <button onClick={onFollow} className={styles.followButton}>
                Follow
            </button>
        </div>
    );
};

export default ArtistListItem;
