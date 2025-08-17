package com.zodus.template.api.mappers;

import com.zodus.template.api.dtos.requests.CreateSalesRecordRequest;
import com.zodus.template.api.dtos.requests.UpdateSalesRecordRequest;
import com.zodus.template.api.dtos.responses.SalesRecordResponse;
import com.zodus.template.application.services.OrderService;
import com.zodus.template.domain.models.SalesRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SalesRecordMapper {
  
  private final OrderService orderService;
  private final OrderMapper orderMapper;
  
  public SalesRecord createSalesRecordRequestToSalesRecord(CreateSalesRecordRequest request) {
    SalesRecord salesRecord = new SalesRecord();
    salesRecord.setOrder(orderService.findById(request.orderId()));
    return salesRecord;
  }

  public SalesRecord updateSalesRecordRequestToSalesRecord(UpdateSalesRecordRequest request) {
    SalesRecord salesRecord = new SalesRecord();
    if (request.orderId() != null) {
      salesRecord.setOrder(orderService.findById(request.orderId()));
    }
    return salesRecord;
  }

  public SalesRecordResponse salesRecordToSalesRecordResponse(SalesRecord salesRecord) {
    return new SalesRecordResponse(
        salesRecord.getId(),
        orderMapper.orderToOrderResponse(salesRecord.getOrder()),
        salesRecord.getCreatedAt(),
        salesRecord.getUpdatedAt()
    );
  }
}
