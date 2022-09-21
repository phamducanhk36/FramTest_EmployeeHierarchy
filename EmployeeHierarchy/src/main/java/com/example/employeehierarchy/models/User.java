package com.example.employeehierarchy.models;

import com.example.employeehierarchy.jwt.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User implements UserDetails {

    @Id
    private String username;

    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //default ADMIN role for demo
        return Collections.singletonList(new SimpleGrantedAuthority(JwtTokenUtil.ADMIN_ROLE));
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
