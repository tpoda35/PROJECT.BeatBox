type Music = {
    id: number;
    title: string;
    artist: string;
    coverUrl: string;
};

const dummyMusic: Music[] = [
    {
        id: 1,
        title: "Blinding Lights",
        artist: "The Weeknd",
        coverUrl: "https://via.placeholder.com/100",
    },
    {
        id: 2,
        title: "Levitating",
        artist: "Dua Lipa",
        coverUrl: "https://via.placeholder.com/100",
    },
    {
        id: 3,
        title: "Shape of You",
        artist: "Ed Sheeran",
        coverUrl: "https://via.placeholder.com/100",
    },
];

const TrackList = () => {
    return (
        <section style={{ padding: "20px", flex: 1 }}>
            <h2>Music Library</h2>

            <div style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
                {dummyMusic.map((music) => (
                    <div
                        key={music.id}
                        style={{
                            display: "flex",
                            alignItems: "center",
                            gap: "15px",
                            padding: "10px",
                            border: "1px solid #ddd",
                            borderRadius: "8px",
                        }}
                    >
                        <img
                            src={music.coverUrl}
                            alt={music.title}
                            style={{ width: "80px", height: "80px", borderRadius: "8px" }}
                        />

                        <div>
                            <h3 style={{ margin: 0 }}>{music.title}</h3>
                            <p style={{ margin: 0, color: "#666" }}>{music.artist}</p>
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
};

export default TrackList;
