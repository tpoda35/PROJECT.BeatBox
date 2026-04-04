import styles from './TrackPlayBar.module.css';
import TrackPlayer from "../trackPlayer/TrackPlayer.tsx";

const TrackPlayBar = () => {

    return (
        <div className={styles.trackPlayBar}>
            <TrackPlayer url="http://localhost:8090/api/tracks/stream/80cb4a3e-06b1-4922-81cf-38cde52b57d9" />
        </div>
    );
};

export default TrackPlayBar;
