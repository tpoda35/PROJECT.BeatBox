import { useEffect, useRef, useState } from "react";
import WaveSurfer from "wavesurfer.js";

export default function WaveformPlayer({ url }: {url: string}) {
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
            height: 80,
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
        <div style={{
            width: "700px",
            padding: "20px",
            borderRadius: "12px",
            background: "#111",
            color: "white"
        }}>
            <div style={{ display: "flex", alignItems: "center", gap: "15px" }}>
                <button
                    onClick={togglePlay}
                    style={{
                        background: "#ff5500",
                        border: "none",
                        borderRadius: "50%",
                        width: "50px",
                        height: "50px",
                        fontSize: "18px",
                        cursor: "pointer",
                        color: "white"
                    }}
                >
                    {isPlaying ? "⏸" : "▶"}
                </button>

                <div style={{ flex: 1 }}>
                    <div ref={waveformRef} />
                </div>

                <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <span style={{ fontSize: "12px" }}>🔊</span>
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
        </div>
    );
}
