package org.chern.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ClientSecurityBeans {

    /*
    Требование аутентификации пользователя к любым ресурсам.
    Схема: доступ к ресурсу - пеерадресация spring security на keycloak - ввод логина и пароля -
    spring security получает ключ доступа - обращение на end-point юзера с запросом прав -
    создание http-сессии.
    */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .build();
    }
}
