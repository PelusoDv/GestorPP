package com.incade.gestorpp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // ❌ .disable() directo está deprecado
            .cors(cors -> {})             // habilita CORS usando tu WebConfig
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // permite libre acceso
            )
            .httpBasic(withDefaults());   // habilita Basic Auth (si quisieras usarlo)

        return http.build();
    }
}