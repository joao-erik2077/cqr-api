package com.zodus.template.api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zodus.template.utils.JsonFormatPatterns;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
    UUID id,
    String name,
    String description,
    Double price,
    String imageUrl,
    List<CategoryResponse> categories,
    @JsonFormat(pattern = JsonFormatPatterns.ISO_DATE)
    LocalDateTime createdAt,
    @JsonFormat(pattern = JsonFormatPatterns.ISO_DATE)
    LocalDateTime updatedAt
) {
}
