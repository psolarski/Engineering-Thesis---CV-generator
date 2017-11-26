package pl.beng.thesis.outlook.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class AuthHelperService {

    @Value("${outlook.url.authority}")
    private String authority;

    @Value("${outlook.url.authority}" + "${outlook.url.authorization}")
    private String authorizeUrl;

    @Value("${outlook.url.authority}" + "${outlook.url.token}")
    private String tokenUrl;

    private static String[] scopes = {
            "User.Read",
            "Mail.Read",
            "Mail.Send"
    };

    @Value("${outlook.app.id}")
    private String appId;

    @Value("${outlook.app.password}")
    private String appPassword;

    @Value("${outlook.url.redirectUrl}")
    private String redirectUrl;


    /**
     * Trim and return scopes as singe
     * string instead of String Array
     *
     * @return Single String with scopes
     */
    private String getScopes() {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(scopes).forEach(scope -> sb.append(scope)
                .append(" "));
        return sb.toString().trim();
    }

    /**
     * Build login URL with given client id, password,
     * scopes and redirection URL.
     *
     * @return URI to outlook 365 authorization page
     * @throws URISyntaxException
     */
    public URI getLoginUrl() throws URISyntaxException {

        URIBuilder uriBuilder = new URIBuilder(authorizeUrl);
        uriBuilder.addParameter("client_id", appId);
        uriBuilder.addParameter("redirect_uri", redirectUrl);
        uriBuilder.addParameter("response_type", "code");
        uriBuilder.addParameter("scope", getScopes());
        uriBuilder.addParameter("response_mode", "form_post");

        return uriBuilder.build();
    }


    /**
     * Return URI to Azure AD Token endpoint
     * @return
     * @throws URISyntaxException
     */
    public URI getAccessTokenUrl() throws URISyntaxException {

        return new URIBuilder(tokenUrl).build();
    }


    /**
     * Build JSON body to receive access token from
     * Azure AD Token endpoint.
     *
     * @param code given after user authorization
     * @return MultiValueMap with parameters.
     */
    public MultiValueMap<String, String> createAccessTokenBody(String code) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("client_id", appId);
        map.add("client_secret", appPassword);
        map.add("redirect_uri", redirectUrl);
        return map;
    }
}