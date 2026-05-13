import styles from './TrackPlayBar.module.css';
import TrackPlayer from "../trackPlayer/TrackPlayer.tsx";
import {useTrack} from "../track/TrackContext.tsx";

const BASE_URL = "http://localhost:8090/api/tracks/stream";

const TrackPlayBar = () => {
    const { selectedTrackId } = useTrack();

    if (!selectedTrackId) return null;

    const streamUrl = `${BASE_URL}/${selectedTrackId}`;

    return (
        <div className={styles.trackPlayBar}>
            <TrackPlayer url={streamUrl} />
        </div>
    );
};

export default TrackPlayBar;