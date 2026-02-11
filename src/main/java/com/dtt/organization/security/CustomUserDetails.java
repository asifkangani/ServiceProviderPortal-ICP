package com.dtt.organization.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serial;
import java.io.Serializable;

import java.util.Collection;

public class CustomUserDetails implements UserDetails, Serializable {

    
    @Serial
    private static final long serialVersionUID = 1L;

    private final String email;
    private final String fullName;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String email,
                             String fullName,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String getUsername() {
        return email; // IMPORTANT
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

