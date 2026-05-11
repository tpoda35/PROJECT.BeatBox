export type UploadAudioProps = {
    value: File | null;
    onChange: (file: File) => void;
    onClear: () => void;
    title?: string;
    subtitle?: string;
    hint?: string;
    clearLabel?: string;
    className?: string;
}