package ru.prooftechit.smh.configuration.security.claims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.domain.model.User;

/**
 * Encapsulates access token claims.
 *
 * @author Roman Zdoronok
 */
public class AccessTokenClaims extends SMHTokenClaims {

    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_SESSION_ID = "sessionId";

    public AccessTokenClaims(Claims claims) {
        super(claims);
    }

    public Long getUserId() {
        return claims.get(CLAIM_USER_ID, Long.class);
    }

    public UserRole getRole() {
        return UserRole.valueOf(claims.get(CLAIM_ROLE, String.class));
    }

    public Long getSessionId() {
        return claims.get(CLAIM_SESSION_ID, Long.class);
    }

    public static Claims build(String username, User user, Long sessionId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AccessTokenClaims.CLAIM_USER_ID, user.getId());
        claims.put(AccessTokenClaims.CLAIM_ROLE, user.getRole().name());
        claims.put(AccessTokenClaims.CLAIM_SESSION_ID, sessionId);
        return claims;
    }
}
