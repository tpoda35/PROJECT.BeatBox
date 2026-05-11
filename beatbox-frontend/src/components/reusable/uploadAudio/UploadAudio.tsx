import { useState } from "react";
import styles from "./UploadAudio.module.css";
import * as React from "react";
import type {UploadAudioProps} from "./types/UploadAudioProps.ts";

const AudioIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24"
         fill="none" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round">
        <path d="M9 18V5l12-2v13" />
        <circle cx="6" cy="18" r="3" />
        <circle cx="18" cy="16" r="3" />
    </svg>
);

const WaveformIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24"
         fill="none" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round">
        <line x1="2" y1="12" x2="2" y2="12" />
        <line x1="6" y1="8" x2="6" y2="16" />
        <line x1="10" y1="5" x2="10" y2="19" />
        <line x1="14" y1="8" x2="14" y2="16" />
        <line x1="18" y1="10" x2="18" y2="14" />
        <line x1="22" y1="12" x2="22" y2="12" />
    </svg>
);

const UploadAudio = ({
                         value,
                         onChange,
                         onClear,
                         title = "Upload Audio",
                         subtitle = "Drag & drop or click to browse",
                         hint = "MP3, WAV, FLAC, AAC up to 50MB",
                         clearLabel = "Remove file",
                         className = "",
                     }: UploadAudioProps) => {
    const [isDragging, setIsDragging] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const applyFile = (f: File) => {
        if (!f.type.startsWith("audio/")) {
            setError("Only audio files are supported.");
            return;
        }
        setError(null);
        onChange(f);
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const f = e.target.files?.[0];
        if (f) applyFile(f);
        e.target.value = "";
    };

    const handleDragOver = (e: React.DragEvent) => {
        e.preventDefault();
        setIsDragging(true);
    };

    const handleDragLeave = () => setIsDragging(false);

    const handleDrop = (e: React.DragEvent) => {
        e.preventDefault();
        setIsDragging(false);
        const f = e.dataTransfer.files[0];
        if (f) applyFile(f);
    };

    const handleClear = (e: React.MouseEvent) => {
        e.preventDefault();
        setError(null);
        onClear();
    };

    const formatSize = (bytes: number) =>
        bytes < 1_000_000
            ? `${(bytes / 1000).toFixed(0)} KB`
            : `${(bytes / 1_000_000).toFixed(1)} MB`;

    return (
        <div className={`${styles.wrapper} ${className}`}>
            <label
                className={`${styles.uploadBox} ${isDragging ? styles.dragging : ""} ${value ? styles.hasFile : ""}`}
                onDragOver={handleDragOver}
                onDragLeave={handleDragLeave}
                onDrop={handleDrop}
            >
                {value ? (
                    <div className={styles.fileRow}>
                        <div className={styles.fileIcon}><WaveformIcon /></div>
                        <div className={styles.fileInfo}>
                            <span className={styles.fileName}>{value.name}</span>
                            <span className={styles.fileSize}>{formatSize(value.size)}</span>
                        </div>
                    </div>
                ) : (
                    <div className={styles.uploadContent}>
                        <div className={styles.iconWrapper}><AudioIcon /></div>
                        <span className={styles.uploadTitle}>{title}</span>
                        <span className={styles.uploadSubtitle}>{subtitle}</span>
                        <span className={styles.uploadHint}>{hint}</span>
                    </div>
                )}
                <input type="file" accept="audio/*" onChange={handleInputChange} hidden />
            </label>

            {error && <span className={styles.errorText}>{error}</span>}

            {value && (
                <button className={styles.clearButton} onClick={handleClear}>
                    {clearLabel}
                </button>
            )}
        </div>
    );
};

export default UploadAudio;