package com.retro.retroapp.util;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditAwareImplementation implements AuditorAware<String> {

    //in order to support @CreatedUser annotation used in JPA auditing, this implementation
    //makes use of SecurityContextHolder, which is responsible for holding Authentication object
    //for a logged in user. The principal is retrieved from this object to access the username
    //of a logged in user, which will be persisted with the comment object, once a particular
    //user creates it.

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.of(((User) authentication.getPrincipal()).getUsername());
    }
}
