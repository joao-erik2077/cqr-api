package com.zodus.template.api.dtos.requests;

public record RegisterRequest(
    String username,
    String password
) {
}
