package com.gym.exercises.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/train/create/{trainerId}").hasRole("STAFF")
                        .requestMatchers(HttpMethod.POST, "/train/register/user/{userId}").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET, "/train/{trainerId}").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET, "/train/{trainerId}/expiring-in-10-days").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET, "/train/{trainerId}/expired").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET, "/train").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET, "/train/{trainId}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/train/user/{userId}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/train/users-affiliated/{trainId}").hasRole("STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/train/delete/{trainId}").hasRole("STAFF")
                        .requestMatchers(HttpMethod.PUT, "/train/update/{trainId}").hasRole("STAFF")
                        .requestMatchers(HttpMethod.PUT, "/train/extend/{trainId}").hasRole("STAFF")
                        .requestMatchers(HttpMethod.PUT, "/train/deactivate/{trainId}").hasRole("STAFF")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

