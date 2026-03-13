package com.javaweb.filters;

import com.javaweb.components.JwtTokenUtil;
import com.javaweb.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  @Value("${api.prefix}")
  private String apiPrefix;

  private final UserDetailsService userDetailsService;
  private final JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      if (isBypassToken(request)) {
        filterChain.doFilter(request, response);
        return;
      }

      final String authHeader = request.getHeader("Authorization");

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return;
      }

      final String token = authHeader.substring(7);
      final String email = jwtTokenUtil.extractEmail(token);

      if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserEntity userDetails = (UserEntity) userDetailsService.loadUserByUsername(email);

        if (jwtTokenUtil.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities()
            );

          authenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
          );

          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
  }

  private boolean isBypassToken(@NonNull HttpServletRequest request) {
    final List<Pair<String, String>> bypassTokens = Arrays.asList(
//      Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
      Pair.of(String.format("%s/admin/users/login", apiPrefix), "POST"),
      Pair.of(String.format("%s/forgot-password", apiPrefix), "POST"),
      Pair.of(String.format("%s/test/encode", apiPrefix), "GET"),
      Pair.of(String.format("%s/admin/password/forgot", apiPrefix), "POST"),
      Pair.of(String.format("%s/admin/password/verify-otp", apiPrefix), "POST"),
      Pair.of(String.format("%s/admin/password/reset", apiPrefix), "POST")
    );

    for (Pair<String, String> item : bypassTokens) {
      if (request.getServletPath().contains(item.getFirst())
        && request.getMethod().equals(item.getSecond())) {
        return true;
      }
    }
    return false;
  }
}