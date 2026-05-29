import type {TrackDto} from "../../track/track/apiDto/TrackDto.ts";

export type ListeningHistoryDto = {
    trackDto: TrackDto;
    createdAt: string;
}