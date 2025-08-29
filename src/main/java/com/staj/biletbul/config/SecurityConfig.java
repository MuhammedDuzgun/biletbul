package com.staj.biletbul.config;

import com.staj.biletbul.security.CustomUserDetailsService;
import com.staj.biletbul.security.JwtAuthenticationEntryPoint;
import com.staj.biletbul.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/v3/**").permitAll();
                    request.requestMatchers("/api/auth/**").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/users").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll();
                    request.requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("USER");
                    request.requestMatchers(HttpMethod.GET, "/api/users/{id}/events").hasRole("USER");
                    request.requestMatchers(HttpMethod.GET, "/api/users/{id}/tickets").hasRole("USER");
                    request.requestMatchers(HttpMethod.GET, "/api/organizers").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/organizers/{id}").permitAll();
                    request.requestMatchers(HttpMethod.DELETE, "/api/organizers/{id}").hasRole("ORGANIZER");
                    request.requestMatchers(HttpMethod.GET, "/api/organizers/{id}/events").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/events").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/api/events").hasRole("ORGANIZER");
                    request.requestMatchers(HttpMethod.GET, "/api/events/event-status").permitAll();
                    request.requestMatchers(HttpMethod.GET,  "/api/events/{id}").permitAll();
                    request.requestMatchers(HttpMethod.PUT, "/api/events/{id}").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.DELETE, "/api/events/{id}").hasRole("ORGANIZER");
                    request.requestMatchers(HttpMethod.GET, "/api/events/{id}/seats").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/events/{id}/seats-available").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/events/{id}/users").hasRole("ORGANIZER");
                    request.requestMatchers(HttpMethod.GET, "/api/event-categories").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/api/event-categories").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/api/event-categories/{id}").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/event-categories/{id}/events").permitAll();
                    request.requestMatchers(HttpMethod.DELETE, "/api/event-categories/{id}").hasRole("ADMIN");

                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
