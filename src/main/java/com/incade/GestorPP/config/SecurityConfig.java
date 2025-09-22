package com.incade.gestorpp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // 🚨 Solo para desarrollo
            .cors() // usa WebConfig.java
            .and()
            .authorizeHttpRequests()
                .antMatchers("/**").permitAll() // permite libre acceso
                .anyRequest().authenticated() // protege el resto
            .and()
            .httpBasic();

        return http.build();
    }
}