import {createContext, type Dispatch, type ReactNode, type SetStateAction, useContext, useState} from 'react';

type AuthContextType = {
    initialized: boolean;
    setInitialized: Dispatch<SetStateAction<boolean>>;
    authenticated: boolean;
    setAuthenticated: Dispatch<SetStateAction<boolean>>;
    loading: boolean;
    setLoading: Dispatch<SetStateAction<boolean>>;
};

export const AuthContext = createContext<AuthContextType | null>(null);

type AuthProviderProps = {
    children: ReactNode;
};

export const AuthProvider = ({ children }: AuthProviderProps) => {
    const [initialized, setInitialized] = useState<boolean>(false);
    const [authenticated, setAuthenticated] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(true);

    const keycloakData: AuthContextType = {
        initialized,
        setInitialized,
        authenticated,
        setAuthenticated,
        loading,
        setLoading,
    };

    return (
        <AuthContext.Provider value={keycloakData}>
            {children}
        </AuthContext.Provider>
    );
};

export function useSharedAuth(): AuthContextType {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
}
