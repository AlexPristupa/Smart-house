package ru.prooftechit.smh.configuration.security.claims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Encapsulates access token claims.
 *
 * @author Roman Zdoronok
 */
public class RefreshTokenClaims extends SMHTokenClaims {

    private static final String CLAIM_TOKEN = "token";
    private static final String CLAIM_INSECURE = "insecure";

    public RefreshTokenClaims(Claims claims) {
        super(claims);
    }

    public String getToken() {
        return claims.get(CLAIM_TOKEN, String.class);
    }

    public Boolean isInsecure() {
        return claims.get(CLAIM_INSECURE, Boolean.class);
    }

    public static Claims build(String username, String tokenUuid, boolean insecure) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(RefreshTokenClaims.CLAIM_TOKEN, tokenUuid);
        claims.put(RefreshTokenClaims.CLAIM_INSECURE, insecure);
        return claims;
    }
}
