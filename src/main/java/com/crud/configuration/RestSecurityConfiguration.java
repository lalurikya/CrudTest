package com.crud.configuration;

import com.crud.component.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
public class RestSecurityConfiguration {//ini untuk mengatur si security/mbak agar bekerja di API saja(tidak bekerja di MVC)

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception{
        http.securityMatcher("/api/**").csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/api/login").permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).cors((request) -> request
                        .configurationSource(corsConfigurationSource())
                ).addFilterBefore(
                        jwtRequestFilter, UsernamePasswordAuthenticationFilter.class
                ).httpBasic((entry) ->
                    entry.authenticationEntryPoint(authenticationEntryPoint())
                ).exceptionHandling((exception) ->
                    exception.accessDeniedHandler(accessDeniedHandler())
                );
        return http.build();
    }

    private AccessDeniedHandler accessDeniedHandler(){
        return (((request, response, accessDeniedException) -> {
            response.setStatus(403);
            response.getWriter().write("AccessDenied");
        }));
    }

    private AuthenticationEntryPoint authenticationEntryPoint(){
        return (((request, response, accessDeniedException) -> {
            response.setStatus(401);
            response.getWriter().write("Unauthorized request header");
        }));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
