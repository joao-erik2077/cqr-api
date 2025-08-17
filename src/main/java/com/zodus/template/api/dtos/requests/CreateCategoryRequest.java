package com.zodus.template.api.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
    @NotBlank(message = "Name is required")
    String name
) {
}
