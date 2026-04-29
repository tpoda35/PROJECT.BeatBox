import SideBar from "../../components/SideBar/Sidebar.tsx";
import styles from './Home.module.css';
import TrackBrowser from "../../components/track/trackBrowser/TrackBrowser.tsx";

const Home = () => {
    return (
        <div className={styles.container}>
            <SideBar />
            <TrackBrowser />
        </div>
    );
};

export default Home;
