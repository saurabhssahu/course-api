package saurabh.s.sahu.course.api.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean isActive;
    private List<SimpleGrantedAuthority> authorities;

    public MyUserDetails() {
    }

    public MyUserDetails(String username) {
        this.username = username;
    }

    public MyUserDetails(User user) {
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.isActive = user.isActive();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    /**
     * password is hardcoded to 'password', username can be anything
     */
    @Override
    public String getPassword() {
//        return "{noop}password";
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * by default all return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
        return isActive;
    }
}