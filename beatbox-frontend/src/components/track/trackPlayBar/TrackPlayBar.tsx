import styles from './TrackPlayBar.module.css';
import TrackPlayer from "../trackPlayer/TrackPlayer.tsx";
import {useTrack} from "../track/TrackContext.tsx";

const TrackPlayBar = () => {
    const { selectedTrackId } = useTrack();

    if (!selectedTrackId) return null;

    const streamUrl = `http://localhost:8090/api/tracks/${selectedTrackId}/stream`;

    return (
        <div className={styles.trackPlayBar}>
            <TrackPlayer key={selectedTrackId} trackId={selectedTrackId} url={streamUrl} />
        </div>
    );
};

export default TrackPlayBar;