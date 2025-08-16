package com.zodus.template.api.dtos.responses;

import java.util.UUID;

public record UserResponse(
    UUID id,
    String username
) {
}
