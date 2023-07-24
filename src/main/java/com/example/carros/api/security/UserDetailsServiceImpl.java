package com.example.carros.api.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (username.equals("user")) {
            return User
                    .withUsername(username)
                    .password("user")
                    .roles("USER")
                    .passwordEncoder(encoder::encode)
                    .build();

        } else if (username.equals("admin")) {
            return User
                    .withUsername(username)
                    .password("admin")
                    .roles("USER", "ADMIN")
                    .passwordEncoder(encoder::encode)
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }
}
