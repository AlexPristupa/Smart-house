package ru.prooftechit.smh.configuration.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Wraps request in RealIPHttpRequestWrapper
 *
 * @author Roman Zdoronok
 */
public class RealIpFilter extends OncePerRequestFilter
{
    public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException
    {
        chain.doFilter(new RealIpHttpRequestWrapper(request), response);
    }

}
