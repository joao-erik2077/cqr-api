package com.zodus.template.application.services;

import com.zodus.template.domain.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthenticationService {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final RefreshTokenService refreshTokenService;

  public final User login(User user) {
    var usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
    var authentication = this.authenticationManager.authenticate(usernamePasswordAuthentication);
    return (User) authentication.getPrincipal();
  }

  public final User register(User user) throws ResponseStatusException {
    boolean userExists = userService.findByUsername(user.getUsername()).isPresent();
    if (userExists) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    String encryptedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encryptedPassword);
    User savedUser = userService.save(user);

    return savedUser;
  }

  public final UserDetails refresh(String refreshToken) throws ResponseStatusException {
    try {
      return refreshTokenService.extractUser(refreshToken);
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
    } catch (UsernameNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }
  }
}
