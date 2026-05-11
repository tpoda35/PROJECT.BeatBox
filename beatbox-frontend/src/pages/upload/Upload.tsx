import { useState, type SetStateAction } from "react";
import styles from "./Upload.module.css";
import UploadImage from "../../components/reusable/uploadImage/UploadImage.tsx";
import UploadAudio from "../../components/reusable/uploadAudio/UploadAudio.tsx";
import Form from "../../components/reusable/form/Form.tsx";
import type { FormField } from "../../components/reusable/form/types/FormField.ts";
import type { FormValues } from "../../components/reusable/form/types/FormValues.ts";
import { useSharedApi } from "../../api/ApiContext.tsx";

const FIELDS: FormField[] = [
    {
        type: "text",
        name: "title",
        label: "Title",
        placeholder: "Track title",
        required: true,
    },
    {
        type: "text",
        name: "artists",
        label: "Artist(s)",
        placeholder: "Artist(s) name",
        required: true,
    },
    {
        type: "select",
        name: "genre",
        label: "Genre",
        placeholder: "Select a genre",
        required: true,
        options: [
            { label: "Electronic", value: "electronic" },
            { label: "Hip-Hop", value: "hip-hop" },
            { label: "Pop", value: "pop" },
            { label: "Rock", value: "rock" },
            { label: "Jazz", value: "jazz" },
            { label: "Classical", value: "classical" },
            { label: "R&B", value: "rnb" },
            { label: "Ambient", value: "ambient" },
            { label: "Folk", value: "folk" },
            { label: "Metal", value: "metal" },
            { label: "Other", value: "other" },
        ],
    },
    {
        type: "text",
        name: "tags",
        label: "Tag(s)",
        placeholder: "Hardstyle, techno...",
        required: false,
    },
    {
        type: "textarea",
        name: "description",
        label: "Description",
        placeholder: "Enter a description",
    },
    {
        type: "radio",
        name: "visibility",
        label: "Visibility",
        required: true,
        options: [
            { label: "Public", value: "public" },
            { label: "Private", value: "private" },
        ],
    },
];

const Upload = () => {
    const [image, setImage] = useState("");
    const [imageFile, setImageFile] = useState<File | null>(null);
    const [audioFile, setAudioFile] = useState<File | null>(null);
    const [formValues, setFormValues] = useState<FormValues>({
        visibility: "public",
    });

    const api = useSharedApi();

    const handleChange = (name: string, value: string | boolean) => {
        setFormValues((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (values: FormValues) => {
        if (!audioFile) {
            console.warn("No audio file selected.");
            return;
        }

        const formData = new FormData();
        formData.append("file", audioFile);
        if (imageFile) formData.append("cover", imageFile);

        // Append all form values
        Object.entries(values).forEach(([key, val]) => {
            formData.append(key, String(val));
        });

        const result = await api.post("/tracks/upload", formData);
        console.log("Uploaded:", result);
    };

    return (
        <div className={styles.container}>
            <div className={styles.leftSide}>
                <UploadImage
                    value={image}
                    onChange={(imageUrl: SetStateAction<string>, file: File) => {
                        setImage(imageUrl as string);
                        setImageFile(file);
                    }}
                    onClear={() => {
                        setImage("");
                        setImageFile(null);
                    }}
                />
                <UploadAudio
                    value={audioFile}
                    onChange={(file) => {
                        setAudioFile(file);
                        // Pre-fill title from filename if not already set
                        setFormValues((prev) =>
                            prev.title
                                ? prev
                                : { ...prev, title: file.name.replace(/\.[^/.]+$/, "") }
                        );
                    }}
                    onClear={() => setAudioFile(null)}
                />
            </div>

            <div className={styles.rightSide}>
                <Form
                    fields={FIELDS}
                    values={formValues}
                    onChange={handleChange}
                    onSubmit={handleSubmit}
                    submitLabel="Upload Track"
                />
            </div>
        </div>
    );
};

export default Upload;