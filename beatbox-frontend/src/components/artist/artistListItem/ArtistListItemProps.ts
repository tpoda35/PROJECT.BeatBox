export type ArtistListItemProps = {
    name: string;
    followers: number;
    tracks: number;
    imageUrl: string;
    isVerified: boolean;
    isFollowing: boolean;
    onFollow?: () => void;
};