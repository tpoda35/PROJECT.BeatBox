import type {ReactNode} from "react";
import {createContext, useContext, useEffect, useState} from "react";
import type {TrackContextType} from "./types/TrackContextType";
import {useSharedApi} from "../../../api/ApiContext.tsx";

const TrackContext = createContext<TrackContextType | null>(null);

export const TrackProvider = ({ children }: { children: ReactNode }) => {
    const [selectedTrackId, setSelectedTrackId] = useState<string | null>(null);

    const api = useSharedApi();

    useEffect(() => {
        const addToListeningHistory = async () => {
            try {
                await api.post(`/tracks/history/${selectedTrackId}`)
            } catch (err) {
                console.error("Failed to add to listening history:", err);
            }
        }

        addToListeningHistory();
    }, [selectedTrackId]);

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

}