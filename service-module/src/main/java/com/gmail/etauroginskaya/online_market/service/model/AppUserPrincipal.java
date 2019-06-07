package com.gmail.etauroginskaya.online_market.service.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AppUserPrincipal implements UserDetails {

    private UserDTO user;
    private Collection<GrantedAuthority> authorities;

    public AppUserPrincipal(UserDTO user) {
        this.user = user;
        authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true;
    }
<<<<<<< HEAD
=======

    public Long getId() {
        return user.getId();
    }
>>>>>>> develop
}
