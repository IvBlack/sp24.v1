package org.chern.manager.security;

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

    /**
     * Получение пользователя из базы данных, сопоставление в контексте UserDetails.
     * Ленивая операция getAuthorities() компенсируется
     * транзакционным аспектом, в который обернут метод.
     *
     * @param login
     * @return
     * @throws UsernameNotFoundException
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
