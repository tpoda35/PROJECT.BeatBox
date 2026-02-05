import {useSharedAuth} from "./auth/AuthContext.tsx";
import {useSharedApi} from "./api/ApiContext.tsx";

export default function App() {
    const { login, logout, register, authenticated, user } = useSharedAuth();

    const api = useSharedApi();
    const loadSomething = async () => {
        const data = await api.get("/admin");
        console.log(data);
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
                </>
            )}
        </div>
    );
}
