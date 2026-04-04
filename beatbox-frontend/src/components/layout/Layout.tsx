import {Outlet} from "react-router";
import Navbar from "../navbar/Navbar.tsx";
import styles from "./Layout.module.css"
import TrackPlayBar from "../track/trackPlayBar/TrackPlayBar.tsx";

const Layout = () => {
    return (
        <>
            <Navbar />
            <main className={styles.mainContainer}>
                <Outlet />
            </main>
            <TrackPlayBar />
        </>
    );
};

export default Layout;