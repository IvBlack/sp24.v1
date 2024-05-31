package org.chern.manager.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Configuration
public class ClientSecurityBeans {

    /*
        Контекст безопасности.
        Требование аутентификации пользователя с определенной ролью к ресурсам.
        Схема: доступ к ресурсу - переадресация spring security на keycloak - ввод логина и пароля -
        spring security получает ключ доступа - обращение на end-point юзера с запросом прав -
        создание http-сессии.
        В контекст безопасности добавлена поддержка oauth2Client.
    */
    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().hasRole("MANAGER"))
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .build();
    }


    /*
        Компонет дополнительно загружает список прав пользователя
        из параметра groups сгененрированного ID-токена KeyCloak - централизованного хранилища юзеров,
        использующего протокол openIdConnect.
        Решение объединяет права, извлеченные spring security из ключа доступа с кастомными правами.
    */
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2UserService() {
        OidcUserService oidcUserService = new OidcUserService();
        return userRequest -> {
            OidcUser oidcUser = oidcUserService.loadUser(userRequest);
            List<GrantedAuthority> authorities =
                    Stream.concat(oidcUser.getAuthorities().stream(),
                    Optional.ofNullable(oidcUser.getClaimAsStringList("groups"))
                            .orElseGet(List::of)
                            .stream()
                            .filter(user -> user.startsWith("ROLE_"))
                            .map(SimpleGrantedAuthority::new)
                            .map(GrantedAuthority.class::cast))
                            .toList();
            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }
}
