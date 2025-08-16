package com.zodus.template.api.mappers;

import com.zodus.template.api.dtos.requests.LoginRequest;
import com.zodus.template.api.dtos.requests.RegisterRequest;
import com.zodus.template.api.dtos.responses.AuthenticationResponse;
import com.zodus.template.api.dtos.responses.UserResponse;
import com.zodus.template.domain.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationMapper {
  private final UserMapper userMapper;

  public final User registerRequestToUser(RegisterRequest registerRequest) {
    return new User(
        registerRequest.username(),
        registerRequest.password()
    );
  }

  public final AuthenticationResponse userToAuthenticationResponse(User user, String token, String refreshToken) {
    UserResponse userResponse = userMapper.userToUserDto(user);

    return new AuthenticationResponse(
        userResponse,
        token,
        refreshToken
    );
  }

  public User loginRequestToUser(LoginRequest loginRequest) {
    return new User(
        loginRequest.username(),
        loginRequest.password()
    );
  }
}