package com.github.simplefocals.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails extends User implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //make a new grantedAuthorityList
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        //make a List of the roles
        List<Role> userRoles = user.getRoles();
        //add each role to the grantedAuthorityList
        for (Role role: userRoles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
        // then return grantedAuthority instances
        return grantedAuthorityList;
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
}
