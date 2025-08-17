package com.zodus.template.api.dtos.requests;

import java.util.UUID;

public record UpdateSalesRecordRequest(
    UUID orderId
) {
}
