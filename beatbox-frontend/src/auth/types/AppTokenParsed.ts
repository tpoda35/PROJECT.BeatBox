import type { KeycloakTokenParsed } from "keycloak-js";

export interface AppTokenParsed extends KeycloakTokenParsed {
    preferred_username?: string;
    email?: string;
    given_name?: string;
    family_name?: string;
    realm_access?: {
        roles: string[];
    };
}