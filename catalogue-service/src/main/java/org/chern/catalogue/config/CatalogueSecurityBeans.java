package org.chern.catalogue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class CatalogueSecurityBeans {

    /**
    * Реализация oauth2-аутентификации между сервисами с подавлением сессий
    * и без csrf-проверок.
    *
    */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/catalogue-api/products")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.PUT, "/catalogue-api/products/{productId:\\d}")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.DELETE, "/catalogue-api/products/{productId:\\d}")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.GET)
                        .hasAuthority("SCOPE_view_catalogue")
                        .anyRequest().denyAll())
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults()))
        .build();
    }


    /**
    * Реализация basic-аутентификации между сервисами с подавлением сессий.
    * Сессии генерируются servlet-контейнером, но не будут использоваться в цепочке фильтров.
    *
    */
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         return http.
//                 authorizeHttpRequests(authorizeHttpRequests ->
//                         authorizeHttpRequests.requestMatchers("/catalogue-api/**")
//                 .hasRole("SERVICE"))
//                 .httpBasic(Customizer.withDefaults())
//                 .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))
//         .build();
//     }
}