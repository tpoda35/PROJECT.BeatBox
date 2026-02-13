export type ArtistListItemProps = {
    name: string;
    followers: number;
    songs: number;
    imageUrl: string;
    verified?: boolean;
    onFollow?: () => void;
};