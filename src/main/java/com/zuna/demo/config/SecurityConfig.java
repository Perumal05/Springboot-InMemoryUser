package com.zuna.demo.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable CSRF for simplicity (for APIs)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml",
                    "/home"
                ).permitAll()
                .requestMatchers("/api/users/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .permitAll()
                .defaultSuccessUrl("/dashboard", true) // always redirect after login
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("alice")
            .password(passwordEncoder.encode("alice123"))
            .roles("USER")
            .build();

        UserDetails admin = User.withUsername("bob")
            .password(passwordEncoder.encode("bob123"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
