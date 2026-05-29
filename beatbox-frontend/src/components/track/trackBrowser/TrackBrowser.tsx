import Track from "../track/Track.tsx";
import TrackSection from "../trackSection/TrackSection.tsx";
import styles from './TrackBrowser.module.css';
import {useSharedApi} from "../../../api/ApiContext.tsx";
import {useEffect, useState} from "react";
import type {TrackDto} from "../track/apiDto/TrackDto.ts";

const TrackBrowser = () => {
    const api = useSharedApi();

    const [tracks, setTracks] = useState<TrackDto[]>([]);

    console.log('Tracks: ', tracks);

    useEffect(() => {
        const fetchTracks = async () => {
            try {
                const result = await api.get<TrackDto[]>("/tracks");
                setTracks(result);
            } catch (err) {
                console.error("Failed to fetch artists", err);
            }
        };

        fetchTracks();
    }, [api]);

    return (
        <section className={styles.container}>
            <TrackSection title="Tracks">
                {tracks.map((track) => (
                    <Track
                        key={track.trackId}
                        trackId={track.trackId}
                        title={track.title}
                        artist={track.artists.join(", ")}
                        coverUrl="https://picsum.photos/300/300?random=1"
                    />
                ))}
            </TrackSection>
        </section>
    );
};

export default TrackBrowser;