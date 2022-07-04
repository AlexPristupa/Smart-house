package ru.prooftechit.smh.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.configuration.properties.SecurityProperties;
import ru.prooftechit.smh.configuration.security.claims.AccessTokenClaims;
import ru.prooftechit.smh.configuration.security.claims.SMHTokenClaims;
import ru.prooftechit.smh.configuration.security.claims.RefreshTokenClaims;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String TOKEN_PARAMETER = "token";

    private final SecurityProperties securityProperties;

    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(securityProperties.getJwt().getSecret().getBytes());
    }

    public String createAccessToken(String username, User user, boolean insecure, Long sessionId) {
        Claims claims = AccessTokenClaims.build(username, user, sessionId);
        Date now = new Date();
        user.setLastActivityAt(now.toInstant());
        Duration validityDuration = insecure
            ? securityProperties.getJwt().getInsecure().getAccessTokenValiditySeconds()
            : securityProperties.getJwt().getRegular().getAccessTokenValiditySeconds();
        Date validity = new Date(now.getTime() + validityDuration.toMillis());
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public RefreshToken createRefreshToken(String username, boolean insecure) {
        Date now = new Date();
        Duration validityDuration = insecure
            ? securityProperties.getJwt().getInsecure().getRefreshTokenValiditySeconds()
            : securityProperties.getJwt().getRegular().getRefreshTokenValiditySeconds();
        Date validity = new Date(now.getTime() + validityDuration.toMillis());
        String tokenUuid = UUID.randomUUID().toString();
        Claims claims = RefreshTokenClaims.build(username, tokenUuid, insecure);
        return RefreshToken.builder()
                .expireAt(validity.toInstant())
                .uuid(tokenUuid)
                .token(Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(validity)
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact())
                .build();
    }

    public Optional<AccessTokenClaims> resolveTokenHeader(HttpServletRequest req) {
        return resolveTokenHeader(req.getHeader(HttpHeaders.AUTHORIZATION));
    }

    public Optional<AccessTokenClaims> resolveTokenHeader(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return validateToken(bearerToken.substring(7), AccessTokenClaims.class);
        }
        return Optional.empty();
    }

    public Optional<AccessTokenClaims> resolveTokenParameter(HttpServletRequest req) {
        String tokenParam = req.getParameter(TOKEN_PARAMETER);
        if (tokenParam != null && !tokenParam.isEmpty()) {
            return validateToken(tokenParam, AccessTokenClaims.class);
        }
        return Optional.empty();
    }

    public Optional<RefreshTokenClaims> resolveRefreshToken(String refreshToken) {
        return validateToken(refreshToken, RefreshTokenClaims.class);
    }

    private <T extends SMHTokenClaims> Optional<T> validateToken(String token, Class<T> tClass) {
        try {
            Constructor<T> cons = tClass.getConstructor(Claims.class);
            return Optional.of(cons.newInstance(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()));
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired: {}", Optional.ofNullable(expEx.getClaims()).map(Claims::getSubject).orElse(null));
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (SignatureException sEx) {
            log.error("Invalid signature");
        } catch (Exception e) {
            log.error("invalid token");
        }
        return Optional.empty();
    }


}
