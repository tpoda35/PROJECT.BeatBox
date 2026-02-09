import {useSharedAuth} from "./auth/AuthContext.tsx";
import {useSharedApi} from "./api/ApiContext.tsx";
import MusicPlayer from "./components/MusicPlayer/MusicPlayer.tsx";

export default function App() {
    const { login, logout, register, authenticated, user } = useSharedAuth();

    const api = useSharedApi();
    const loadSomething = async () => {
        const data = await api.get("/test");
        console.log(data);
    };

    const uploadTrack = async () => {
        const fileInput = document.createElement("input");
        fileInput.type = "file";
        fileInput.accept = "audio/*";

        fileInput.onchange = async () => {
            if (!fileInput.files || fileInput.files.length === 0) return;

            const file = fileInput.files[0];

            const formData = new FormData();
            formData.append("title", file.name);
            formData.append("file", file);

            const result = await api.post("/tracks/upload", formData);

            console.log("Uploaded:", result);
        };

        fileInput.click();
    };

    return (
        <div>
            {!authenticated ? (
                <>
                    <button onClick={login}>Login</button>
                    <button onClick={register}>Register</button>
                </>
            ) : (
                <>
                    <p>Welcome, {user?.username}</p>
                    <button onClick={logout}>Logout</button>
                    <button onClick={loadSomething}>Call API</button>
                    <button onClick={uploadTrack}>Upload Track</button>
                    <MusicPlayer url="http://localhost:8090/api/tracks/stream/3f9a1877-5058-4f2e-9c7d-64c9bd47572e" />
                </>
            )}
        </div>
    );
}
