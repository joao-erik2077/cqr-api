package com.zodus.template.api.dtos.responses;

public record AuthenticationResponse(
    UserResponse user,
    String token,
    String refreshToken
) {
}
