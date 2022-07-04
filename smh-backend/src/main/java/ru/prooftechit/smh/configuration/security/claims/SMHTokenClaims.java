package ru.prooftechit.smh.configuration.security.claims;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Common jwt token claims holder.
 *
 * @author Roman Zdoronok
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SMHTokenClaims {
    protected final Claims claims;

    public String getUsername() {
        return claims.getSubject();
    }
}
