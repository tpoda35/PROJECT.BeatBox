import {useState, useEffect, useRef, useCallback} from "react";
import keycloak from "./KeyCloak";
import type { User } from "./types/User.ts";

const tokenRefreshThreshold = 30;

export const useKeycloak = () => {
    const [authenticated, setAuthenticated] = useState(false);
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);

    const refreshInterval = useRef<number | null>(null);

    const getUser = (): User | null => {
        const tokenParsed = keycloak.tokenParsed as Record<string, any> | undefined;
        if (!tokenParsed) return null;

        return {
            id: tokenParsed.sub,
            username: tokenParsed.preferred_username,
            email: tokenParsed.email,
            firstName: tokenParsed.given_name,
            lastName: tokenParsed.family_name,
            roles: tokenParsed.realm_access?.roles ?? [],
        };
    };

    const updateToken = useCallback(async (minValidity: number = tokenRefreshThreshold) => {
        try {
            const refreshed = await keycloak.updateToken(minValidity);
            if (refreshed) {
                console.log("Token refreshed");
                setUser(getUser());
            }
            return keycloak.token;
        } catch (err) {
            console.error("Failed to refresh token", err);
            throw err;
        }
    }, []);

    useEffect(() => {
        const initKeycloak = async () => {
            try {
                const auth = await keycloak.init({
                    pkceMethod: "S256",
                    checkLoginIframe: false,
                    onLoad: "check-sso",
                    silentCheckSsoRedirectUri:
                        window.location.origin + "/silent-check-sso.html",
                });

                setAuthenticated(auth);
                setUser(auth ? getUser() : null);

                if (auth) {
                    refreshInterval.current = window.setInterval(() => {
                        updateToken().catch(() => {
                            if (refreshInterval.current) clearInterval(refreshInterval.current);
                        });
                    }, 300_000); // 5min
                }
            } finally {
                setLoading(false);
            }
        };

        initKeycloak();

        return () => {
            if (refreshInterval.current) clearInterval(refreshInterval.current);
        };
    }, []);

    const login = async () => {
        await keycloak.login();
    };

    const logout = () => {
        keycloak.logout({ redirectUri: window.location.origin });
        setAuthenticated(false);
        setUser(null);

        if (refreshInterval.current) clearInterval(refreshInterval.current);
    };

    const register = async () => {
        await keycloak.register();
    };

    return {
        login,
        logout,
        register,
        authenticated,
        user,
        updateToken,
        loading,
    };
};
