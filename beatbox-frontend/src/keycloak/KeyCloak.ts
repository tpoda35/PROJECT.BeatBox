import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
    url: "http://localhost:8080",
    realm: "beatbox-realm",
    clientId: "beatbox-frontend"
});

export default keycloak;
