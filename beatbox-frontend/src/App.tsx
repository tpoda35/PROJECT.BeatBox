import {useKeycloak} from "./keycloak/useKeyCloak.ts";

export default function App() {
    const { login, logout, register, authenticated, user } = useKeycloak();

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
                </>
            )}
        </div>
    );
}
