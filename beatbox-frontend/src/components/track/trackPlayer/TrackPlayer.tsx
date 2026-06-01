import {useEffect, useRef, useState} from "react";
import WaveSurfer from "wavesurfer.js";
import styles from "./TrackPlayer.module.css";
import {IconPlayerPauseFilled, IconPlayerPlayFilled, IconVolume} from "@tabler/icons-react";
import RangeSlider from "../../reusable/rangeSlider/RangeSlider.tsx";
import {useSharedApi} from "../../../api/ApiContext.tsx";

interface TrackPlayerProps {
    trackId: string;
    url: string;
}

export default function TrackPlayer({ trackId, url }: TrackPlayerProps) {
    const waveformRef = useRef<HTMLDivElement | null>(null);
    const wavesurferRef = useRef<WaveSurfer | null>(null);
    const viewCountedRef = useRef(false);        // ensures we only count once per mount
    const listenedSecondsRef = useRef(0);        // accumulates actual listened time
    const lastTimeRef = useRef<number | null>(null); // tracks last timeupdate position

    const [isPlaying, setIsPlaying] = useState(false);
    const [volume, setVolume] = useState(1);

    const api = useSharedApi();

    useEffect(() => {
        if (!waveformRef.current) return;

        wavesurferRef.current = WaveSurfer.create({
            container: waveformRef.current,
            waveColor: "#999",
            progressColor: "#CA3E47",
            height: 35,
            barWidth: 2,
            barGap: 2,
            fillParent: true,

            // KEY CHANGE: MediaElement backend uses a native <audio> element.
            // This means WaveSurfer sends HTTP Range requests as the user listens,
            // instead of downloading the entire file up front via Axios.
            //
            // Benefits:
            // - Playback starts immediately (no waiting for full download)
            // - Seeking works properly
            // - Server observes real streaming behavior (used for view counting)
            // - Memory efficient — only buffers what's needed
            backend: "MediaElement",
        });

        wavesurferRef.current.load(url);

        wavesurferRef.current.on("play", () => setIsPlaying(true));
        wavesurferRef.current.on("pause", () => {
            setIsPlaying(false);
            lastTimeRef.current = null; // stop accumulating on pause
        });
        wavesurferRef.current.on("finish", () => {
            setIsPlaying(false);
            lastTimeRef.current = null;
        });

        // Accumulate real listened time on every timeupdate tick.
        //
        // Why not just check currentTime >= 30?
        // Because a user could seek to 0:29 and get a free view count.
        // Accumulating actual delta time is seek-proof.
        wavesurferRef.current.on("timeupdate", (currentTime: number) => {
            if (viewCountedRef.current) return;

            if (lastTimeRef.current !== null) {
                const delta = currentTime - lastTimeRef.current;

                // Only count forward deltas — ignore seeks backward
                if (delta > 0 && delta < 2) {
                    listenedSecondsRef.current += delta;
                }
            }

            lastTimeRef.current = currentTime;

            if (listenedSecondsRef.current >= 30) {
                viewCountedRef.current = true;

                api.post(`/tracks/${trackId}/view`).catch((err) => {
                    console.error("Failed to record view:", err);
                });
            }
        });

        return () => {
            wavesurferRef.current?.destroy();
        };
    }, [url]);

    const togglePlay = () => {
        wavesurferRef.current?.playPause();
    };

    return (
        <div className={styles.container}>
            <button onClick={togglePlay} className={styles.playButton}>
                {isPlaying ? <IconPlayerPauseFilled size={20} /> : <IconPlayerPlayFilled size={20} />}
            </button>

            <div className={styles.waveformWrapper}>
                <div ref={waveformRef} />
            </div>

            <div className={styles.volumeContainer}>
                <IconVolume size={16}/>
                <RangeSlider
                    value={volume}
                    min={0}
                    max={1}
                    step={0.01}
                    onChange={(value) => {
                        setVolume(value);
                        wavesurferRef.current?.setVolume(value);
                    }}
                    leftColor="var(--accent)"
                    rightColor="var(--panel-2)"
                />
            </div>
        </div>
    );
}