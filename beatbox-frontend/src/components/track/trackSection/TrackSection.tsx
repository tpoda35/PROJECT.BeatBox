import type {TrackSectionProps} from "./types/TrackSectionProps.ts";
import styles from './TrackSection.module.css';
import TrackSlider from "../trackSlider/TrackSlider.tsx";

const TrackSection = ({ title, children }: TrackSectionProps) => {
    return (
        <div className={styles.mainContainer}>
            <h5 className={styles.title}>{title}</h5>
            <TrackSlider>
                {children}
            </TrackSlider>
        </div>
    );
};

export default TrackSection;
