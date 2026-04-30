import SidebarSection from "./sidebarSection/SidebarSection.tsx";
import styles from './Sidebar.module.css'
import ArtistListItem from "../artist/artistListItem/ArtistListItem.tsx";
import TrackListItem from "../track/trackListItem/TrackListItem.tsx";
import {useSharedApi} from "../../api/ApiContext.tsx";
import {useEffect, useState} from "react";
import type {ArtistDto} from "./apiDto/ArtistDto.ts";

const Sidebar = () => {
    const api = useSharedApi();

    const [artists, setArtists] = useState<ArtistDto[]>([]);

    useEffect(() => {
        const fetchArtists = async () => {
            try {
                const result = await api.get<ArtistDto[]>("/artists/recommended");
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
        } catch (err) {
            console.error("Failed to toggle follow", err);
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

            <SidebarSection title="LISTENING HISTORY">
                <TrackListItem
                    artist="Holy Priest, Bloodlust"
                    title="Bloodlust & Holy Priest - Hit The Floor"
                    coverUrl="/pb.jpg"
                    plays={2370000}
                    likes={50900}
                    reposts={1473}
                    comments={298}
                />

                <TrackListItem
                    artist="Holy Priest, Manji"
                    title="Holy Priest & Manji - No Balance"
                    coverUrl="/pb.jpg"
                    plays={2750000}
                    likes={60600}
                    reposts={534}
                    comments={372}
                />

                <TrackListItem
                    artist="Madmize"
                    title="Warface - Mashup 6.0 (Madmize Kick Edit)"
                    coverUrl="/pb.jpg"
                    plays={708000}
                    likes={201000}
                    reposts={1937}
                    comments={2036}
                />
            </SidebarSection>

            <SidebarSection title="LIKES">
                <TrackListItem
                    artist="Holy Priest, Bloodlust"
                    title="Bloodlust & Holy Priest - Hit The Floor"
                    coverUrl="/pb.jpg"
                    plays={2370000}
                    likes={50900}
                    reposts={1473}
                    comments={298}
                />

                <TrackListItem
                    artist="Holy Priest, Manji"
                    title="Holy Priest & Manji - No Balance"
                    coverUrl="/pb.jpg"
                    plays={2750000}
                    likes={60600}
                    reposts={534}
                    comments={372}
                />

                <TrackListItem
                    artist="Madmize"
                    title="Warface - Mashup 6.0 (Madmize Kick Edit)"
                    coverUrl="/pb.jpg"
                    plays={708000}
                    likes={201000}
                    reposts={1937}
                    comments={2036}
                />
            </SidebarSection>
        </aside>
    );
};

export default Sidebar;
