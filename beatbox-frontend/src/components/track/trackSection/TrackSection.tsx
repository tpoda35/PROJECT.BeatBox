import type {TrackSectionProps} from "./types/TrackSectionProps.ts";
import styles from './TrackSection.module.css';

const TrackSection = ({ title, children }: TrackSectionProps) => {
    return (
        <div className={styles.mainContainer}>
            <h5 className={styles.title}>{title}</h5>
            <div className={styles.container}>{children}</div>
        </div>
    );
};

export default TrackSection;
