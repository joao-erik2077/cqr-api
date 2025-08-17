package com.zodus.template.api.dtos.requests;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateSalesRecordRequest(
    @NotNull(message = "Order ID is required")
    UUID orderId
) {
}
