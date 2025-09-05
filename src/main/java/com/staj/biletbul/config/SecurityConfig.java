package com.staj.biletbul.config;

import com.staj.biletbul.security.CustomUserDetailsService;
import com.staj.biletbul.security.JwtAccessDeniedHandler;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    //general
                    request.requestMatchers("/swagger-ui.html").permitAll();
                    request.requestMatchers("/swagger-ui/**").permitAll();
                    request.requestMatchers("/v3/api-docs/**").permitAll();
                    request.requestMatchers("/v3/api-docs.yaml").permitAll();
                    request.requestMatchers("/api/auth/**").permitAll();
                    //users
                    request.requestMatchers(HttpMethod.GET, "/api/users").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll();
                    request.requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("USER");
                    request.requestMatchers(HttpMethod.GET, "/api/users/{id}/events").hasRole("USER");
                    request.requestMatchers(HttpMethod.GET, "/api/users/{id}/tickets").hasRole("USER");
                    //organizers
                    request.requestMatchers(HttpMethod.GET, "/api/organizers").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/organizers/{id}").permitAll();
                    request.requestMatchers(HttpMethod.DELETE, "/api/organizers/{id}").hasRole("ORGANIZER");
                    request.requestMatchers(HttpMethod.GET, "/api/organizers/{id}/events").permitAll();
                    //events
                    request.requestMatchers(HttpMethod.GET, "/api/events").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/api/events").hasRole("ORGANIZER");
                    request.requestMatchers(HttpMethod.GET, "/api/events/event-status").permitAll();
                    request.requestMatchers(HttpMethod.GET,  "/api/events/{id}").permitAll();
                    request.requestMatchers(HttpMethod.PUT, "/api/events/{id}").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.DELETE, "/api/events/{id}").hasRole("ORGANIZER");
                    request.requestMatchers(HttpMethod.GET, "/api/events/{id}/seats").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/events/{id}/seats-available").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/events/{id}/users").hasRole("ORGANIZER");
                    //event-categories
                    request.requestMatchers(HttpMethod.GET, "/api/event-categories").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/api/event-categories").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/api/event-categories/{id}").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/event-categories/{id}/events").permitAll();
                    request.requestMatchers(HttpMethod.DELETE, "/api/event-categories/{id}").hasRole("ADMIN");
                    //artists
                    request.requestMatchers(HttpMethod.GET, "/api/artists").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/api/artists").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/api/artists/{id}").permitAll();
                    request.requestMatchers(HttpMethod.DELETE, "/api/artists/{id}").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/api/artists/{id}/events").permitAll();
                    //cities
                    request.requestMatchers(HttpMethod.GET, "/api/cities").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/api/cities").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/api/cities/{id}").permitAll();
                    request.requestMatchers(HttpMethod.DELETE, "/api/cities/{id}").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/api/cities/{id}/events").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/api/cities/{id}/venues").permitAll();
                    //venues
                    request.requestMatchers(HttpMethod.GET, "/api/venues").permitAll();
                    request.requestMatchers(HttpMethod.POST, "/api/venues").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/api/venues/{id}").permitAll();
                    request.requestMatchers(HttpMethod.DELETE, "/api/venues/{id}").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/api/venues/{id}/events").permitAll();
                    //books
                    request.requestMatchers(HttpMethod.POST, "/api/books").hasRole("USER");
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // React frontend
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
