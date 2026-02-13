import { useEffect, useState } from "react";
import { NavLink } from "react-router";
import { Sling as Hamburger } from "hamburger-react";
import styles from "./Navbar.module.css";
import { useSharedAuth } from "../../auth/AuthContext.tsx";

const Navbar = () => {
    const [isOpen, setOpen] = useState(false);

    const getLinkClass = ({ isActive }: { isActive: boolean }) =>
        isActive ? `${styles.link} ${styles.active}` : styles.link;

    const closeMenu = () => setOpen(false);

    const { login, logout, register, authenticated, user } = useSharedAuth();

    useEffect(() => {
        const handleResize = () => {
            if (window.innerWidth > 800) {
                setOpen(false);
            }
        };

        window.addEventListener("resize", handleResize);
        return () => window.removeEventListener("resize", handleResize);
    }, []);

    return (
        <nav className={styles.navbar}>
            <div className={styles.container}>
                <NavLink
                    to="/"
                    className={`${styles.logo} staatliches-regular`}
                    onClick={closeMenu}
                >
                    BeatBox
                </NavLink>

                {/* Desktop Links */}
                <ul className={styles.navLinks}>
                    <li>
                        <NavLink to="/" className={getLinkClass}>
                            Home
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/feed" className={getLinkClass}>
                            Feed
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/library" className={getLinkClass}>
                            Library
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/search" className={getLinkClass}>
                            Search
                        </NavLink>
                    </li>
                </ul>

                {/* Desktop Right */}
                <div className={styles.navRight}>
                    {!authenticated ? (
                        <>
                            <button onClick={login}>
                                Login
                            </button>
                            <button onClick={register}>
                                Register
                            </button>
                        </>
                    ) : (
                        <>
                            <NavLink
                                to="/profile"
                                className={styles.profileBtn}
                                onClick={closeMenu}
                            >
                                {user?.username ? user.username : "Profile"}
                            </NavLink>

                            <button onClick={logout} className={styles.authBtn}>
                                Logout
                            </button>
                        </>
                    )}
                </div>

                {/* Hamburger (Mobile Only) */}
                <div className={styles.hamburgerWrap}>
                    <Hamburger toggled={isOpen} toggle={setOpen} size={26} />
                </div>
            </div>

            {isOpen && (
                <div className={styles.backdrop} onClick={closeMenu}></div>
            )}

            {/* Mobile Dropdown */}
            {isOpen && (
                <div className={styles.mobileDropdown}>
                    <NavLink to="/" className={styles.mobileLink} onClick={closeMenu}>
                        Home
                    </NavLink>

                    <NavLink to="/feed" className={styles.mobileLink} onClick={closeMenu}>
                        Feed
                    </NavLink>

                    <NavLink to="/library" className={styles.mobileLink} onClick={closeMenu}>
                        Library
                    </NavLink>

                    <NavLink to="/search" className={styles.mobileLink} onClick={closeMenu}>
                        Search
                    </NavLink>

                    {!authenticated ? (
                        <>
                            <button onClick={login} className={styles.authBtnMobile}>
                                Login
                            </button>
                            <button onClick={register} className={styles.authBtnMobile}>
                                Register
                            </button>
                        </>
                    ) : (
                        <>
                            <NavLink
                                to="/profile"
                                className={styles.profileBtnMobile}
                                onClick={closeMenu}
                            >
                                {user?.username ? user.username : "Profile"}
                            </NavLink>

                            <button onClick={logout} className={styles.authBtnMobile}>
                                Logout
                            </button>
                        </>
                    )}
                </div>
            )}
        </nav>
    );
};

export default Navbar;
