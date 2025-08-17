package com.zodus.template.api.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateProductRequest(
    @NotBlank(message = "Name is required")
    String name,
    String description,
    @NotNull(message = "Price is required")
    Double price,
    String imageUrl,
    List<UUID> categories
) {
}
