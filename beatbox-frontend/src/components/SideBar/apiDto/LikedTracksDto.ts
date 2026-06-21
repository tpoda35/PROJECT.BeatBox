import type {TrackDto} from "../../track/track/apiDto/TrackDto.ts";

export type LikedTracksDto = {
    trackDto: TrackDto;
    createdAt: string;
}