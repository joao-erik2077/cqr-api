package com.zodus.template.configurations.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zodus.template.application.services.RefreshTokenService;
import com.zodus.template.application.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
  private final static String AUTH_HEADER = "Authorization";
  private final static String TOKEN_PREFIX = "Bearer ";
  private final TokenService tokenService;
  private final RefreshTokenService refreshTokenService;
  private final Logger logger;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoverToken(request);
    if (token != null && !refreshTokenService.isRefreshToken(token)) {
      UserDetails userDetails = tokenService.extractUser(token);
      Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

      boolean isTokenExpired = tokenService.isTokenNotExpired(token);

      if (isTokenExpired) {
        logger.info("Token for user {} was expired.", userDetails.getUsername());
        throw new TokenExpiredException("Token expired", tokenService.getTokenExpirationDate(token));
      }

      var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader(AUTH_HEADER);
    if (authHeader == null) {
      return null;
    }
    return authHeader.replace(TOKEN_PREFIX, "");
  }
}
