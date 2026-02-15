import type {TrackSectionProps} from "./TrackSectionProps.ts";
import styles from './TrackSection.module.css';

const TrackSection = ({ title, children }: TrackSectionProps) => {
    return (
        <div>
            <h5 className={styles.title}>{title}</h5>
            <div className={styles.container}>{children}</div>
        </div>
    );
};

export default TrackSection;
