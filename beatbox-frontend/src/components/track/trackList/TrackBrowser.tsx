import Track from "../track/Track.tsx";
import TrackSection from "../trackSection/TrackSection.tsx";
import styles from './TrackBrowser.module.css';

const TrackBrowser = () => {
    return (
        <section className={styles.container}>
            <TrackSection title="Recommended tracks">
                <Track id={1} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
                <Track id={2} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
                <Track id={3} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
                <Track id={4} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
                <Track id={5} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
            </TrackSection>

            <TrackSection title="Recently played">
                <Track id={1} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
                <Track id={2} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
                <Track id={3} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
                <Track id={4} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
                <Track id={5} title="LIDZTER - PLAY IT RIGHT" artist="LIDZTER, ROYAL, Jason" coverUrl="https://picsum.photos/300/300?random=1" />
            </TrackSection>
        </section>
    );
};

export default TrackBrowser;
