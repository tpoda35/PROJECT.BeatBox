import {Outlet} from "react-router";
import Navbar from "../navbar/Navbar.tsx";
import styles from "./Layout.module.css"

const Layout = () => {
    return (
        <>
            <Navbar />
            <main className={styles.mainContainer}>
                <Outlet />
            </main>
        </>
    );
};

export default Layout;