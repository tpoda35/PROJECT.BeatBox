import { useEffect, useRef, useState } from "react";
import WaveSurfer from "wavesurfer.js";
import styles from "./TrackPlayer.module.css";
import {IconPlayerPauseFilled, IconPlayerPlayFilled, IconVolume} from "@tabler/icons-react";

export default function TrackPlayer({ url }: { url: string }) {
    const waveformRef = useRef<HTMLDivElement | null>(null);
    const wavesurferRef = useRef<WaveSurfer | null>(null);

    const [isPlaying, setIsPlaying] = useState(false);
    const [volume, setVolume] = useState(1);

    useEffect(() => {
        if (!waveformRef.current) return;

        wavesurferRef.current = WaveSurfer.create({
            container: waveformRef.current,
            waveColor: "#999",
            progressColor: "#ff5500",
            cursorColor: "#ff5500",
            height: 35,
            barWidth: 2,
            barGap: 2
        });

        wavesurferRef.current.load(url);

        wavesurferRef.current.on("play", () => setIsPlaying(true));
        wavesurferRef.current.on("pause", () => setIsPlaying(false));
        wavesurferRef.current.on("finish", () => setIsPlaying(false));

        return () => {
            wavesurferRef.current?.destroy();
        };
    }, [url]);

    const togglePlay = () => {
        if (!wavesurferRef.current) return;
        wavesurferRef.current.playPause();
    };

    const handleVolumeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseFloat(e.target.value);
        setVolume(value);

        if (wavesurferRef.current) {
            wavesurferRef.current.setVolume(value);
        }
    };

    return (
        <div className={styles.container}>
            <button onClick={togglePlay} className={styles.playButton}>
                {isPlaying ? <IconPlayerPlayFilled size={20} /> : <IconPlayerPauseFilled size={20} />}
            </button>

            <div className={styles.waveformWrapper}>
                <div ref={waveformRef} />
            </div>

            <div className={styles.volumeContainer}>
                <IconVolume size={12}/>
                <input
                    type="range"
                    min="0"
                    max="1"
                    step="0.01"
                    value={volume}
                    onChange={handleVolumeChange}
                />
            </div>
        </div>
    );
}