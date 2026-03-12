package com.javaweb.config;

import com.javaweb.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  @Value("${api.prefix}")
  private String apiPrefix;

  private final JwtTokenFilter jwtTokenFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .cors(cors -> {})
      .csrf(AbstractHttpConfigurer::disable)
      .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(request -> request
        .requestMatchers("/api/test/**").permitAll()
        .requestMatchers(HttpMethod.POST, apiPrefix + "/admin/users/login").permitAll()

        .requestMatchers(apiPrefix + "/admin/buildings/**").hasRole("ADMIN")
        .requestMatchers(apiPrefix + "/admin/roles/**").hasRole("ADMIN")
        .requestMatchers("/api/admin/users/create").hasRole("ADMIN")
        .requestMatchers("/api/admin/users/**").hasRole("ADMIN")

        .requestMatchers(apiPrefix + "/admin/users/**").hasAnyRole("ADMIN", "STAFF")

        .anyRequest().authenticated()
      );

    return http.build();
  }
}