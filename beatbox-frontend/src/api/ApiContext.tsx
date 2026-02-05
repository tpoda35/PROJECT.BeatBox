import { createContext, useContext, useMemo } from "react";
import ApiService from "./ApiService";
import { useSharedAuth } from "../auth/AuthContext.tsx";
import * as React from "react";
import type {ApiContextType} from "./types/ApiContextType.ts";

export const ApiContext = createContext<ApiService | null>(null);

export const ApiProvider = ({ children }: { children: React.ReactNode }) => {
    const auth = useSharedAuth();

    const api = useMemo(() => new ApiService(auth.updateToken), [auth.updateToken]);

    return (
        <ApiContext.Provider value={api}>
            {children}
        </ApiContext.Provider>
    );
};

export function useSharedApi(): ApiContextType {
    const context = useContext(ApiContext);

    if (!context) throw new Error("useApi must be used within an ApiProvider");

    return context;
}
