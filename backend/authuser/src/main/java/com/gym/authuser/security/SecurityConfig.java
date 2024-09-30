package com.gym.authuser.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/signup").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/register/instructor").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/register/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/check-admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/check-trainer").hasRole("STAFF")
                        .requestMatchers(HttpMethod.POST, "/user/check-customer").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/user/deletecustomer/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/user/deletetrainer/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/user/deleteadmin/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user/password/{userId}").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/user/activate/year/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user/activate/mounth/{userId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user/deactivate/{userId}").hasRole("ADMIN")
                        .anyRequest().authenticated()

                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
