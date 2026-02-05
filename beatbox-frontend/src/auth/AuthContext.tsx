import * as React from "react";
import {createContext, useContext,} from "react";
import {useKeycloak} from "./useKeyCloak.ts";
import type {AuthContextType} from "./types/AuthContextType.ts";

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const keycloakData = useKeycloak();

    return (
        <AuthContext.Provider value={keycloakData}>
            {children}
        </AuthContext.Provider>
    );
};

export function useSharedAuth(): AuthContextType {
    const context = useContext(AuthContext);

    if (!context) throw new Error("useSharedAuth must be used within an AuthProvider");

    return context;
}
