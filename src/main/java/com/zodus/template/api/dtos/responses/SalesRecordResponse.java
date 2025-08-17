package com.zodus.template.api.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zodus.template.utils.JsonFormatPatterns;

import java.time.LocalDateTime;
import java.util.UUID;

public record SalesRecordResponse(
    UUID id,
    OrderResponse order,
    @JsonFormat(pattern = JsonFormatPatterns.ISO_DATE)
    LocalDateTime createdAt,
    @JsonFormat(pattern = JsonFormatPatterns.ISO_DATE)
    LocalDateTime updatedAt
) {
}
