package ru.prooftechit.smh.configuration.security;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.prooftechit.smh.configuration.security.claims.AccessTokenClaims;

/**
 * @author Roman Zdoronok
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final RequestMatcher streamRequestMatcher;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider,
                          UserDetailsService userDetailsService,
                          RequestMatcher streamRequestMatcher) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.streamRequestMatcher = streamRequestMatcher;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
        throws IOException, ServletException {

        Optional<AccessTokenClaims> claims = jwtTokenProvider.resolveTokenHeader(req);
        if (claims.isEmpty() && streamRequestMatcher.matches(req)) {
            claims = jwtTokenProvider.resolveTokenParameter(req);
        }

        if (claims.isPresent()) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get().getUsername());
                SecurityContextHolderWrapper.setAuthentication(userDetails, claims.get());
            } catch (UsernameNotFoundException e) {
                log.warn("Не удалось найти пользователя {}", claims.get().getUsername());
            }
        }

        filterChain.doFilter(req, res);
    }
}
