package com.zodus.template.api.dtos.requests;

import java.util.List;
import java.util.UUID;

public record UpdateOrderRequest(
    List<UUID> productIds
) {
}
