package pl.beng.thesis.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    @Value("${security.constant.secret.key}")
    public String secret;

    @Value("${security.constant.expiration.time}")
    public long expirationTime;

    @Value("${security.constant.token.prefix}")
    public String tokenPrefix;

    @Value("${security.constant.header.string}")
    public String headerString;

    public SecurityConstants() {
    }

    public String getSecret() {
        return secret;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public String getHeaderString() {
        return headerString;
    }
}
