package ru.prooftechit.smh.configuration.security;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.prooftechit.smh.configuration.security.claims.AccessTokenClaims;
import ru.prooftechit.smh.domain.model.User;

/**
 * Типизированная обертка для работы с SecurityContext.
 *
 * @author Roman Zdoronok
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContextHolderWrapper {

    public static Authentication setAuthentication(UserDetails userDetails, AccessTokenClaims claims) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        auth.setDetails(claims);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static User getCurrentUser() {
        return Optional.ofNullable(getAuthentication())
                       .map(authentication -> ((UserDetailsImpl) authentication.getPrincipal()).getUser())
                       .orElse(null);
    }

    public static AccessTokenClaims getCurrentUserClaims() {
        return Optional.ofNullable(getAuthentication())
                       .map(authentication -> (AccessTokenClaims) authentication.getDetails())
                       .orElse(null);
    }

}
