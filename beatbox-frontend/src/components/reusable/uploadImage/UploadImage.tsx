import { useState } from "react";
import styles from "./UploadImage.module.css";
import type { UploadImageProps } from "./types/UploadImageProps.ts";
import * as React from "react";

const ImageIcon = () => (
    <svg
        xmlns="http://www.w3.org/2000/svg"
        width="40"
        height="40"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        strokeWidth="1.5"
        strokeLinecap="round"
        strokeLinejoin="round"
    >
        <rect x="3" y="3" width="18" height="18" rx="2" ry="2" />
        <circle cx="8.5" cy="8.5" r="1.5" />
        <polyline points="21 15 16 10 5 21" />
    </svg>
);

const UploadImage = ({
                         value,
                         onChange,
                         onClear,
                         title = "Upload Image",
                         subtitle = "Drag & drop or click to browse",
                         hint = "PNG, JPG, WEBP up to 10MB",
                         alt = "preview",
                         clearLabel = "Remove photo",
                         className = "",
                     }: UploadImageProps) => {
    const [isDragging, setIsDragging] = useState(false);

    const applyFile = (file: File) => {
        if (!file.type.startsWith("image/")) return;

        // Activates, when a user tries to override a file, without removing the current
        if (value?.startsWith("blob:")) URL.revokeObjectURL(value);

        onChange(URL.createObjectURL(file), file);
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) applyFile(file);

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
        const file = e.dataTransfer.files[0];
        if (file) applyFile(file);
    };

    const handleClear = (e: React.MouseEvent) => {
        e.preventDefault();

        // Activates, when the user clears the file with the clear button
        if (value?.startsWith("blob:")) URL.revokeObjectURL(value);

        onClear();
    };

    return (
        <div className={`${styles.wrapper} ${className}`}>
            <label
                className={`${styles.uploadBox} ${isDragging ? styles.dragging : ""} ${value ? styles.hasImage : ""}`}
                onDragOver={handleDragOver}
                onDragLeave={handleDragLeave}
                onDrop={handleDrop}
            >
                {value ? (
                    <img src={value} alt={alt} className={styles.preview} />
                ) : (
                    <div className={styles.uploadContent}>
                        <div className={styles.iconWrapper}>
                            <ImageIcon />
                        </div>
                        <span className={styles.uploadTitle}>{title}</span>
                        <span className={styles.uploadSubtitle}>{subtitle}</span>
                        <span className={styles.uploadHint}>{hint}</span>
                    </div>
                )}
                <input
                    type="file"
                    accept="image/*"
                    onChange={handleInputChange}
                    hidden
                />
            </label>

            {value && (
                <button className={styles.clearButton} onClick={handleClear}>
                    {clearLabel}
                </button>
            )}
        </div>
    );
};

export default UploadImage;