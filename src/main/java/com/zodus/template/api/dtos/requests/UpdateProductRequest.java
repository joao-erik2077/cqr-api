package com.zodus.template.api.dtos.requests;

import java.util.List;
import java.util.UUID;

public record UpdateProductRequest(
    String name,
    String description,
    Double price,
    String imageUrl,
    List<UUID> categories
) {
}
