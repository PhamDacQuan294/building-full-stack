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
        .requestMatchers(HttpMethod.POST, apiPrefix + "/admin/password/forgot").permitAll()
        .requestMatchers(HttpMethod.POST, apiPrefix + "/admin/password/verify-otp").permitAll()
        .requestMatchers(HttpMethod.POST, apiPrefix + "/admin/password/reset").permitAll()

        .requestMatchers(HttpMethod.GET, "/api/admin/buildings").hasAuthority("BUILDING_VIEW")
        .requestMatchers(HttpMethod.GET, "/api/admin/buildings/detail/**").hasAuthority("BUILDING_VIEW")
        .requestMatchers(HttpMethod.GET, "/api/admin/buildings/*/staffs").hasAuthority("BUILDING_VIEW")

        .requestMatchers(HttpMethod.POST, "/api/admin/buildings/create").hasAuthority("BUILDING_CREATE")
        .requestMatchers(HttpMethod.POST, "/api/admin/buildings/upload").hasAuthority("BUILDING_CREATE")
        .requestMatchers(HttpMethod.POST, "/api/admin/buildings/assignment").hasAuthority("BUILDING_EDIT")

        .requestMatchers(HttpMethod.PUT, "/api/admin/buildings/edit/**").hasAuthority("BUILDING_EDIT")

        .requestMatchers(HttpMethod.PATCH, "/api/admin/buildings/change-status/**").hasAuthority("BUILDING_EDIT")
        .requestMatchers(HttpMethod.PATCH, "/api/admin/buildings/change-multi").hasAuthority("BUILDING_EDIT")

        .requestMatchers(HttpMethod.DELETE, "/api/admin/buildings/delete/**").hasAuthority("BUILDING_DELETE")

//        .requestMatchers(apiPrefix + "/admin/buildings/**").hasRole("ADMIN")
//        .requestMatchers(apiPrefix + "/admin/roles/**").hasRole("ADMIN")
//        .requestMatchers("/api/admin/users/create").hasRole("ADMIN")
//        .requestMatchers("/api/admin/users/**").hasRole("ADMIN")

        .requestMatchers(HttpMethod.GET, "/api/admin/users/**").hasAuthority("USER_VIEW")
        .requestMatchers(HttpMethod.POST, "/api/admin/users/create").hasAuthority("USER_CREATE")
        .requestMatchers(HttpMethod.PUT, "/api/admin/users/edit/**").hasAuthority("USER_EDIT")
        .requestMatchers(HttpMethod.DELETE, "/api/admin/users/delete/**").hasAuthority("USER_DELETE")

        .requestMatchers(HttpMethod.GET, "/api/admin/customers/**").hasAuthority("CUSTOMER_VIEW")
        .requestMatchers(HttpMethod.POST, "/api/admin/customers/create").hasAuthority("CUSTOMER_CREATE")
        .requestMatchers(HttpMethod.PUT, "/api/admin/customers/edit/**").hasAuthority("CUSTOMER_EDIT")
        .requestMatchers(HttpMethod.DELETE, "/api/admin/customers/delete/**").hasAuthority("CUSTOMER_DELETE")
        .requestMatchers(HttpMethod.POST, "/api/admin/customers/assignment").hasAuthority("CUSTOMER_ASSIGN")

        .requestMatchers("/api/admin/roles/**").hasRole("ADMIN")

//        .requestMatchers(apiPrefix + "/admin/users/**").hasAnyRole("ADMIN", "STAFF")

        .anyRequest().authenticated()
      );

    return http.build();
  }
}