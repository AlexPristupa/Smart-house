package ru.prooftechit.smh.configuration.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Roman Zdoronok
 */
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
