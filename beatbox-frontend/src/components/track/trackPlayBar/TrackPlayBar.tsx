import styles from './TrackPlayBar.module.css';
import TrackPlayer from "../trackPlayer/TrackPlayer.tsx";
import {useTrack} from "../track/TrackContext.tsx";

const TrackPlayBar = () => {
    const { selectedTrackId } = useTrack();

    if (!selectedTrackId) return null;

    // Pass the direct stream URL — no Axios blob download needed.
    // WaveSurfer's MediaElement backend will handle range requests natively,
    // just like a browser <audio> element would.
    const streamUrl = `http://localhost:8090/api/tracks/stream/${selectedTrackId}`;

    return (
        <div className={styles.trackPlayBar}>
            <TrackPlayer key={selectedTrackId} trackId={selectedTrackId} url={streamUrl} />
        </div>
    );
};

export default TrackPlayBar;