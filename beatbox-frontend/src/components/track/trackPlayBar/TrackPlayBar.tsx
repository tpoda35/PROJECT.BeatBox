import styles from './TrackPlayBar.module.css';
import TrackPlayer from "../trackPlayer/TrackPlayer.tsx";

const TrackPlayBar = () => {

    return (
        <div className={styles.trackPlayBar}>
            <TrackPlayer url="http://localhost:8090/api/tracks/stream/61bc404e-2016-4ca0-89d6-0e74895dd4a9" />
        </div>
    );
};

export default TrackPlayBar;
