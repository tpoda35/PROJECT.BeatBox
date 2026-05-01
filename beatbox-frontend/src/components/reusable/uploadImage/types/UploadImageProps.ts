export type UploadImageProps = {
    /** Current image URL, controlled by the parent. */
    value: string;
    /** Called with the new object URL and file whenever a new image is picked or dropped. */
    onChange: (imageUrl: string, file: File) => void;
    /** Called when the user clears the image. */
    onClear: () => void;
    /** Label shown inside the box. Default: "Upload Image" */
    title?: string;
    /** Sub-label shown inside the box. Default: "Drag & drop or click to browse" */
    subtitle?: string;
    /** Hint shown inside the box. Default: "PNG, JPG, WEBP up to 10MB" */
    hint?: string;
    /** Alt text for the preview image. Default: "preview" */
    alt?: string;
    /** Text on the remove button. Default: "Remove photo" */
    clearLabel?: string;
    /** Extra class applied to the root wrapper. */
    className?: string;
}