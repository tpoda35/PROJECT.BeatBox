import SidebarSection from "./sidebarSection/SidebarSection.tsx";
import styles from './Sidebar.module.css'
import ArtistListItem from "../artist/artistListItem/ArtistListItem.tsx";
import TrackListItem from "../track/trackListItem/TrackListItem.tsx";
import {useSharedApi} from "../../api/ApiContext.tsx";
import {useEffect, useState} from "react";
import type {ArtistProps} from "./ArtistProps.ts";

const Sidebar = () => {
    const api = useSharedApi();

    const [artists, setArtists] = useState<ArtistProps[]>([]);

    console.log(artists);

    useEffect(() => {
        const fetchArtists = async () => {
            try {
                const result = await api.get<ArtistProps[]>("/artists/recommended");
                setArtists(result);
            } catch (err) {
                console.error("Failed to fetch artists", err);
            }
        };

        fetchArtists();
    }, [api]);

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
                        onFollow={() => alert(`Followed ${artist.preferredUsername}`)}
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
