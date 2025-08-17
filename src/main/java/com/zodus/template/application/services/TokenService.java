package com.zodus.template.application.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zodus.template.configurations.security.TokenProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class TokenService {
  private final static String TOKEN_PREFIX = "Bearer ";
  private final static String AMERICA_SAO_PAULO_OFFSET = "-03:00";
  private final static String TOKEN_TYPE = "access";
  private final TokenProperties tokenProperties;
  private final UserService userService;

  public String generateToken(UserDetails userDetails) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(tokenProperties.getSecret());
      return JWT.create()
          .withIssuer(tokenProperties.getIssuer())
          .withSubject(userDetails.getUsername())
          .withClaim("type", TOKEN_TYPE)
          .withExpiresAt(generateExpirationDate())
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while generating token", exception);
    }
  }

  public boolean isTokenExpired(String token) {
    return LocalDateTime.now().toInstant(ZoneOffset.of(AMERICA_SAO_PAULO_OFFSET)).isAfter(getTokenExpirationDate(token));
  }

  public Instant getTokenExpirationDate(String token) {
    DecodedJWT decodedJWT = JWT.decode(token);
    return decodedJWT.getExpiresAtAsInstant();
  }

  public UserDetails extractUser(String bearerToken) throws UsernameNotFoundException {
    String token = extractJWTToken(bearerToken);
    String subject = extractSubject(token);
    return userService.findByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  private String extractSubject(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(tokenProperties.getSecret());
      return JWT.require(algorithm)
          .withIssuer(tokenProperties.getIssuer())
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException exception) {
      return "";
    }
  }

  private String extractJWTToken(String bearerToken) {
    return bearerToken.replace(TOKEN_PREFIX, "");
  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusDays(tokenProperties.getExpirationTimeInDays())
        .toInstant(ZoneOffset.of(AMERICA_SAO_PAULO_OFFSET));
  }
}
