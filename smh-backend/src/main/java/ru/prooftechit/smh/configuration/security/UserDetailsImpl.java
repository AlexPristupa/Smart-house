package ru.prooftechit.smh.configuration.security;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.prooftechit.smh.api.enums.UserStatus;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = -8998476601455433654L;

    private final String username;
    private final User user;

    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String username, User user) {
        this.username = username;
        this.user = user;

        this.enabled = user.getStatus() != UserStatus.ARCHIVED && user.getStatus() != UserStatus.DELETED;
        List<GrantedAuthority> newAuthorities = new ArrayList<>();
        newAuthorities.add(new SimpleGrantedAuthority(SecurityConfig.role(user.getRole())));

        this.authorities = newAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User getUser() {
        return user;
    }
}
