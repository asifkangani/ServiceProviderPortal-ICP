package com.dtt.organization.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/", "/login",
                            "/eoi-redirect", "/logout",
                            "/icons/**", "/css/**", "/js/**", "/img/**", "/assets/**",
                            "/api/public/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .logout(logout -> logout.disable())
            .requestCache(requestCache -> requestCache.disable())
            .sessionManagement(session -> session
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    .expiredUrl("/?sessionExpired=true")
            );

    return http.build();
}



}

