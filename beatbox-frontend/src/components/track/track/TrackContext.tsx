import { createContext, useContext, useState } from "react";
import type { ReactNode } from "react";
import type { TrackContextType } from "./types/TrackContextType";

const TrackContext = createContext<TrackContextType | null>(null);

export const TrackProvider = ({ children }: { children: ReactNode }) => {
    const [selectedTrackId, setSelectedTrackId] = useState<string | null>(null);

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