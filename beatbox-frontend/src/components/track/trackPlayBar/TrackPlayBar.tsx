import styles from './TrackPlayBar.module.css';
import TrackPlayer from "../trackPlayer/TrackPlayer.tsx";
import {useTrack} from "../track/TrackContext.tsx";
import {useEffect, useState} from "react";
import {useSharedApi} from "../../../api/ApiContext.tsx";

const TrackPlayBar = () => {
    const { selectedTrackId } = useTrack();
    const api = useSharedApi();
    const [blobUrl, setBlobUrl] = useState<string | null>(null);

    useEffect(() => {
        if (!selectedTrackId) return;

        let objectUrl: string;

        api.get<Blob>(`/tracks/stream/${selectedTrackId}`, {
            responseType: "blob",
        }).then((blob) => {
            objectUrl = URL.createObjectURL(blob);
            setBlobUrl(objectUrl);
        });

        return () => {
            // Revoke previous blob URL to avoid memory leaks
            if (objectUrl) URL.revokeObjectURL(objectUrl);
            setBlobUrl(null);
        };
    }, [selectedTrackId]);

    if (!selectedTrackId || !blobUrl) return null;

    return (
        <div className={styles.trackPlayBar}>
            <TrackPlayer url={blobUrl} />
        </div>
    );
};

export default TrackPlayBar;