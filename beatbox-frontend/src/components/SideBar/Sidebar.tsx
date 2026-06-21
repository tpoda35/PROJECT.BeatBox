import SidebarSection from "./sidebarSection/SidebarSection.tsx";
import styles from './Sidebar.module.css'
import ArtistListItem from "../artist/artistListItem/ArtistListItem.tsx";
import TrackListItem from "../track/trackListItem/TrackListItem.tsx";
import {useSharedApi} from "../../api/ApiContext.tsx";
import {useEffect, useState} from "react";
import type {ArtistDto} from "./apiDto/ArtistDto.ts";
import type {ListeningHistoryDto} from "./apiDto/ListeningHistoryDto.ts";
import {useSharedAuth} from "../../auth/AuthContext.tsx";
import toast from "react-hot-toast";
import axios from "axios";
import type {LikedTracksDto} from "./apiDto/LikedTracksDto.ts";

const Sidebar = () => {
    const api = useSharedApi();
    const auth = useSharedAuth();

    const [artists, setArtists] = useState<ArtistDto[]>([]);
    const [listeningHistory, setListeningHistory] = useState<ListeningHistoryDto[]>([]);
    const [likedTracks, setLikedTracks] = useState<LikedTracksDto[]>([]);

    console.log('listeningHistory', listeningHistory);
    console.log('likedTracks', likedTracks);

    // Authenticated requests
    useEffect(() => {
        if (!auth.authenticated) return;

        const fetchListeningHistory = async () => {
            try {
                // The content is set for the pagination, bcs the result.content has the actual data
                const result = await api.get<{ content: ListeningHistoryDto[] }>("/tracks/history");
                setListeningHistory(result.content);
            } catch (err) {
                console.error("Failed to fetch listening history", err);
            }
        };

        const fetchLikedTracks = async () => {
            try {
                const result = await api.get<{ content: LikedTracksDto[] }>("/me/liked-tracks")
                setLikedTracks(result.content)
            } catch (err) {
                console.error("Failed to fetch liked tracks", err);
            }
        }
        
        fetchListeningHistory();
        fetchLikedTracks();
    }, [api, auth.authenticated]);


    // Unauthenticated requests
    useEffect(() => {
        const fetchArtists = async () => {
            try {
                const result = await api.get<ArtistDto[]>("/me/recommended-artists");
                setArtists(result);
            } catch (err) {
                console.error("Failed to fetch artists", err);
            }
        };
        
        fetchArtists();
    }, [api]);

    const handleFollowToggle = async (artistId: string) => {
        const artist = artists.find(a => a.artistId === artistId);
        if (!artist) return;

        const isFollowing = artist.isFollowing;

        try {
            if (isFollowing) {
                await api.delete(`/follows/${artistId}`);
            } else {
                await api.post(`/follows/${artistId}`);
            }

            // update UI after success
            setArtists(prev =>
                prev.map(a =>
                    a.artistId === artistId
                        ? {
                            ...a,
                            isFollowing: !isFollowing,
                            followerCount: a.followerCount + (isFollowing ? -1 : 1)
                        }
                        : a
                )
            );
        } catch (err: unknown) {
            if (axios.isAxiosError(err)) {
                if (err.response?.status === 401) {
                    toast.error("You need to be logged in to do this.");
                } else {
                    toast.error("Something went wrong.");
                }
            } else {
                toast.error("Something went wrong.");
            }
        }
    }

    const handleLikeToggle = async (trackId: string) => {
        const track = listeningHistory.find(e => e.trackDto.trackId === trackId);
        if (!track) return;

        const isLiked = track.trackDto.isLiked;

        try {
            if (isLiked) {
                await api.delete(`/tracks/${trackId}/like`);
            } else {
                await api.post(`/tracks/${trackId}/like`);
            }

            // update UI after success
            setListeningHistory(prev =>
                prev.map(e =>
                    e.trackDto.trackId === trackId
                        ? {
                            ...e,
                            trackDto: {
                                ...e.trackDto,
                                isLiked: !isLiked,
                                likeCount: e.trackDto.likeCount + (isLiked ? -1 : 1)
                            }
                        }
                        : e
                )
            );
        } catch (err) {
            console.error("Failed to toggle like", err);
        }
    };

    return (
        <aside className={styles.container}>
            <SidebarSection title="RECOMMENDED ARTISTS">
                {artists.map((artist) => (
                    <ArtistListItem
                        key={artist.artistId}
                        name={artist.preferredUsername}
                        followers={artist.followerCount}
                        tracks={artist.trackCount}
                        imageUrl="/pp.jpg"
                        isVerified={artist.isVerified}
                        isFollowing={artist.isFollowing}
                        onFollow={() => handleFollowToggle(artist.artistId)}
                    />
                ))}
            </SidebarSection>

            {
                auth.authenticated &&
                    <>
                        <SidebarSection title="LISTENING HISTORY">
                            {listeningHistory.map((entry, index) => (
                                <TrackListItem
                                    key={`${entry.trackDto.trackId}-${index}`}
                                    artist={entry.trackDto.artists.join(", ")}
                                    title={entry.trackDto.title}
                                    coverUrl="/pb.jpg"
                                    plays={entry.trackDto.viewCount}
                                    likes={entry.trackDto.likeCount}
                                    reposts={0}
                                    comments={0}
                                    isLiked={entry.trackDto.isLiked}
                                    onLike={() => handleLikeToggle(entry.trackDto.trackId)}
                                />
                            ))}
                        </SidebarSection>

                        <SidebarSection title="LIKED TRACKS">
                            {likedTracks.map((entry, index) => (
                                <TrackListItem
                                    key={`${entry.trackDto.trackId}-${index}`}
                                    artist={entry.trackDto.artists.join(", ")}
                                    title={entry.trackDto.title}
                                    coverUrl="/pb.jpg"
                                    plays={entry.trackDto.viewCount}
                                    likes={entry.trackDto.likeCount}
                                    reposts={0}
                                    comments={0}
                                    isLiked={entry.trackDto.isLiked}
                                    onLike={() => handleLikeToggle(entry.trackDto.trackId)}
                                />
                            ))}
                        </SidebarSection>
                    </>

            }
        </aside>
    );
};

export default Sidebar;
