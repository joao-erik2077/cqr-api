package com.zodus.template.api.controllers;

import com.zodus.template.api.dtos.requests.LoginRequest;
import com.zodus.template.api.dtos.requests.RefreshTokenRequest;
import com.zodus.template.api.dtos.requests.RegisterRequest;
import com.zodus.template.api.dtos.responses.AuthenticationResponse;
import com.zodus.template.api.mappers.AuthenticationMapper;
import com.zodus.template.application.services.AuthenticationService;
import com.zodus.template.application.services.RefreshTokenService;
import com.zodus.template.application.services.TokenService;
import com.zodus.template.domain.models.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;
  private final TokenService tokenService;
  private final RefreshTokenService refreshTokenService;
  private final AuthenticationMapper authenticationMapper;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
    User userLogin = authenticationMapper.loginRequestToUser(request);
    User user = authenticationService.login(userLogin);

    String token = tokenService.generateToken(user);
    String refreshToken = refreshTokenService.generateRefreshToken(user);
    AuthenticationResponse response = authenticationMapper.userToAuthenticationResponse(user, token, refreshToken);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
    User userToBeRegistered = authenticationMapper.registerRequestToUser(request);
    User user = authenticationService.register(userToBeRegistered);

    String token = tokenService.generateToken(user);
    String refreshToken = refreshTokenService.generateRefreshToken(user);
    AuthenticationResponse response = authenticationMapper.userToAuthenticationResponse(user, token, refreshToken);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
    User user = (User) authenticationService.refresh(request.refreshToken());

    String token = tokenService.generateToken(user);
    String refreshToken = refreshTokenService.generateRefreshToken(user);
    AuthenticationResponse response = authenticationMapper.userToAuthenticationResponse(user, token, refreshToken);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}