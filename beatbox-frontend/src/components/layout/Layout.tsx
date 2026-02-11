import {Outlet} from "react-router";
import Navbar from "../navbar/Navbar.tsx";

const Layout = () => {
    return (
        <>
            <Navbar />
            <main>
                <Outlet />
            </main>
        </>
    );
};

export default Layout;