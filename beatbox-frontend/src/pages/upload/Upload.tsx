import { useState, type SetStateAction} from "react";
import styles from "./Upload.module.css";
import UploadImage from "../../components/reusable/uploadImage/UploadImage.tsx";

const Upload = () => {
    const [image, setImage] = useState("");

    return (
        <div className={styles.container}>
            <div className={styles.leftSide}>
                <UploadImage
                    value={image}
                    onChange={(imageUrl: SetStateAction<string>) => setImage(imageUrl)}
                    onClear={() => setImage("")}
                />
            </div>

            <div className={styles.rightSide}>
                {/* your form here */}
            </div>
        </div>
    );
};

export default Upload;