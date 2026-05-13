import {Outlet} from "react-router";
import Navbar from "../navbar/Navbar.tsx";
import styles from "./Layout.module.css"
import TrackPlayBar from "../track/trackPlayBar/TrackPlayBar.tsx";
import {useTrack} from "../track/track/TrackContext.tsx";

const Layout = () => {
    const { selectedTrackId } = useTrack();

    return (
        <>
            <Navbar />
            <main className={`${styles.mainContainer} ${selectedTrackId ? styles.withPlayer : ''}`}>
                <Outlet />
            </main>
            <TrackPlayBar />
        </>
    );
};

export default Layout;