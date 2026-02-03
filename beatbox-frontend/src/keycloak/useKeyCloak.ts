import { useState, useEffect } from "react";
import keycloak from "./KeyCloak.ts";
import type { User } from "../types/User.ts";

const tokenRefreshThreshold = 30; // seconds

export const useKeycloak = () => {
    const [authenticated, setAuthenticated] = useState(false);
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(false);

    let refreshInterval: number | undefined;

    const updateToken = async (minValidity: number = tokenRefreshThreshold): Promise<string | undefined> => {
        try {
            const refreshed = await keycloak.updateToken(minValidity);
            if (refreshed) {
                console.log("Token refreshed");
                setUser(getUser());
            }
            return keycloak.token;
        } catch (error) {
            console.error("Failed to refresh token", error);
            throw error;
        }
    };

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

    const login = async () => {
        setLoading(true);
        try {
            await keycloak.init({ pkceMethod: "S256", checkLoginIframe: false, onLoad: "login-required" });
            setAuthenticated(keycloak.authenticated);
            setUser(getUser());

            if (keycloak.authenticated) {
                refreshInterval = window.setInterval(() => updateToken().catch(() => clearInterval(refreshInterval)), 300_000);
            }
        } finally {
            setLoading(false);
        }
    };

    const logout = () => {
        keycloak.logout({ redirectUri: window.location.origin });
        setAuthenticated(false);
        setUser(null);
        if (refreshInterval) clearInterval(refreshInterval);
    };

    const register = async () => {
        setLoading(true);
        try {
            if (!keycloak?.didInitialize) {
                await keycloak.init({ pkceMethod: "S256", checkLoginIframe: false });
            }

            keycloak.register();
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        // Cleanup interval on unmount
        return () => {
            if (refreshInterval) clearInterval(refreshInterval);
        };
    }, []);

    return { login, logout, register, authenticated, user, updateToken, loading };
};
