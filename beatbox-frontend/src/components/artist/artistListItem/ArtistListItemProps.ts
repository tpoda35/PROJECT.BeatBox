export type ArtistListItemProps = {
    name: string;
    followers: number;
    tracks: number;
    imageUrl: string;
    verified?: boolean;
    onFollow?: () => void;
};