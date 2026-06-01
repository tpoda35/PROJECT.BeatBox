import {createContext, useContext, useEffect, useRef, useState} from "react";
import type { ReactNode } from "react";
import type { TrackContextType } from "./types/TrackContextType";
import {useSharedApi} from "../../../api/ApiContext.tsx";

const TrackContext = createContext<TrackContextType | null>(null);

export const TrackProvider = ({ children }: { children: ReactNode }) => {
    const [selectedTrackId, setSelectedTrackId] = useState<string | null>(null);
    const viewCountedRef = useRef<string | null>(null);
    const timerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

    const api = useSharedApi();

    useEffect(() => {
        if (timerRef.current) clearTimeout(timerRef.current);

        if (!selectedTrackId) return;

        timerRef.current = setTimeout(async () => {
            if (viewCountedRef.current === selectedTrackId) return;

            viewCountedRef.current = selectedTrackId;

            try {
                await api.post(`/tracks/history/${selectedTrackId}`);
                await api.post(`/tracks/${selectedTrackId}/view`);
            } catch (err) {
                console.error("Failed to record play:", err);
            }
        }, 30_000);

        return () => {
            if (timerRef.current) clearTimeout(timerRef.current);
        };
    }, [selectedTrackId, api]);

    return (
        <TrackContext.Provider value={{ selectedTrackId, setSelectedTrackId }}>
            {children}
        </TrackContext.Provider>
    );
};

export const useTrack = (): TrackContextType => {
    const context = useContext(TrackContext);

    if (!context) throw new Error("useTrack must be used within a TrackProvider");

    return context;
};