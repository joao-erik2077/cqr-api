package com.zodus.template.api.mappers;

import com.zodus.template.api.dtos.requests.CreateOrderRequest;
import com.zodus.template.api.dtos.requests.UpdateOrderRequest;
import com.zodus.template.api.dtos.responses.OrderResponse;
import com.zodus.template.application.services.ProductService;
import com.zodus.template.domain.models.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderMapper {
  
  private final ProductService productService;
  private final ProductMapper productMapper;
  
  public Order createOrderRequestToOrder(CreateOrderRequest request) {
    Order order = new Order();
    order.setProducts(
        request.productIds().stream()
            .map(productService::findById)
            .collect(Collectors.toSet())
    );
    return order;
  }

  public Order updateOrderRequestToOrder(UpdateOrderRequest request) {
    Order order = new Order();
    if (request.productIds() != null && !request.productIds().isEmpty()) {
      order.setProducts(
          request.productIds().stream()
              .map(productService::findById)
              .collect(Collectors.toSet())
      );
    }
    return order;
  }

  public OrderResponse orderToOrderResponse(Order order) {
    return new OrderResponse(
        order.getId(),
        order.getProducts().stream()
            .map(productMapper::productToProductResponse)
            .collect(Collectors.toList()),
        order.getSalesRecord() != null ? order.getSalesRecord().getId() : null,
        order.getCreatedAt(),
        order.getUpdatedAt()
    );
  }
}
