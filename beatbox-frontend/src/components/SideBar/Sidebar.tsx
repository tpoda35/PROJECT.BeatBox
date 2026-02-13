import SidebarSection from "./sidebarSection/SidebarSection.tsx";
import styles from './Sidebar.module.css'
import ArtistListItem from "../artist/artistListItem/ArtistListItem.tsx";
import TrackListItem from "../track/trackListItem/TrackListItem.tsx";

const Sidebar = () => {
    return (
        <aside className={styles.container}>
            <SidebarSection title="RECOMMENDED ARTISTS">
                <ArtistListItem
                    name="BVDLM"
                    followers={985}
                    songs={9}
                    imageUrl="/pp.jpg"
                    onFollow={() => alert("Followed BVDLM")}
                />

                <ArtistListItem
                    name="Moxla"
                    followers={4622}
                    songs={28}
                    imageUrl="/pp.jpg"
                    onFollow={() => alert("Followed Moxla")}
                />

                <ArtistListItem
                    name="Docteur Bisous"
                    followers={276}
                    songs={11}
                    verified={true}
                    imageUrl="/pp.jpg"
                    onFollow={() => alert("Followed Docteur Bisous")}
                />
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
