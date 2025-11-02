package com.zuna.demo.config;
import com.zuna.demo.service.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers("/api/users/**").authenticated()
                .anyRequest().permitAll()
            .and()
            .formLogin()
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            .and()
            .logout()
                .permitAll();

        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    //     UserDetails user = User.withUsername("alice")
    //         .password(passwordEncoder.encode("alice123"))
    //         .roles("USER")
    //         .build();

    //     UserDetails admin = User.withUsername("bob")
    //         .password(passwordEncoder.encode("bob123"))
    //         .roles("ADMIN")
    //         .build();

    //     return new InMemoryUserDetailsManager(user, admin);
    // }


    // Custom UserDetailsService bean

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
