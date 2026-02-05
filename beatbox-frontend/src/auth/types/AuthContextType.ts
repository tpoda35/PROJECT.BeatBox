import type {User} from "./User.ts";

export type AuthContextType = {
    authenticated: boolean;
    loading: boolean;
    user: User | null;

    login: () => Promise<void>;
    logout: () => void;
    register: () => Promise<void>;

    updateToken: (minValidity?: number) => Promise<string | undefined>;
};