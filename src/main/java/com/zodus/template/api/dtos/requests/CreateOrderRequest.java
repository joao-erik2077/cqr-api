package com.zodus.template.api.dtos.requests;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
    @NotEmpty(message = "Products list cannot be empty")
    List<UUID> productIds
) {
}
