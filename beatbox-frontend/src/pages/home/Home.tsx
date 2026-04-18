import SideBar from "../../components/SideBar/Sidebar.tsx";
import TrackBrowser from "../../components/track/trackList/TrackBrowser.tsx";
import styles from './Home.module.css';

const Home = () => {
    return (
        <div className={styles.container}>
            <SideBar />
            <TrackBrowser />
        </div>
    );
};

export default Home;
