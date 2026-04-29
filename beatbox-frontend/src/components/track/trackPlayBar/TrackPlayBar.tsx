import styles from './TrackPlayBar.module.css';
import TrackPlayer from "../trackPlayer/TrackPlayer.tsx";

const TrackPlayBar = () => {

    return (
        <div className={styles.trackPlayBar}>
            <TrackPlayer url="http://localhost:8090/api/tracks/stream/26094287-d3ad-447d-9cc7-8940f8d25dd" />
        </div>
    );
};

export default TrackPlayBar;
