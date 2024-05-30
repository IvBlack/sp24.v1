package org.chern.manager.security;

import jakarta.transaction.TransactionScoped;
import lombok.RequiredArgsConstructor;
import org.chern.manager.entity.Authority;
import org.chern.manager.repository.RetailUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RetailUserDetailService implements UserDetailsService {

    private final RetailUserRepository retailUserRepository;

    /*
        Get user from the DB, mapping in the UserDetails context.
        Lazy operation getAuthorities() is compensated by
        transaction aspect in which the method is wrapped.
    */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return this.retailUserRepository.findByLogin(login)
                .map(user -> User.builder()
                        .username(user.getLogin())
                        .password(user.getPassword())
                        .authorities(String.valueOf(user.getAuthorities().stream()
                                .map(Authority::getAuthority)
                                .map(SimpleGrantedAuthority::new)
                                .toList()))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found!".formatted(login)));
    }
}
