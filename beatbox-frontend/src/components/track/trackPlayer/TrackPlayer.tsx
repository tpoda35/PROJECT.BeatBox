import {useEffect, useRef, useState} from "react";
import WaveSurfer from "wavesurfer.js";
import styles from "./TrackPlayer.module.css";
import {IconPlayerPauseFilled, IconPlayerPlayFilled, IconVolume} from "@tabler/icons-react";
import RangeSlider from "../../reusable/rangeSlider/RangeSlider.tsx";

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
            progressColor: "#CA3E47",
            height: 35,
            barWidth: 2,
            barGap: 2,
            fillParent: true
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

    return (
        <div className={styles.container}>
            <button onClick={togglePlay} className={styles.playButton}>
                {isPlaying ? <IconPlayerPauseFilled size={20} /> :  <IconPlayerPlayFilled size={20} />}
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