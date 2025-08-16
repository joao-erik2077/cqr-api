package com.zodus.template.api.dtos.requests;

public record LoginRequest(
    String username,
    String password
) {
}
