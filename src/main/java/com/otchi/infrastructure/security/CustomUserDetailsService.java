package com.otchi.infrastructure.security;


import com.otchi.domain.users.models.Account;
import com.otchi.domain.users.models.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private AccountRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating {}", username);
        Optional<Account> userFromDatabase = userRepository.findOneByUsername(username);
        return userFromDatabase.map(user -> {
            if (!user.isEnabled()) {
                throw new UserNotActivatedException("User " + username + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(username,
                    user.getPassword(),
                    grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the " +
                "database"));
    }
}