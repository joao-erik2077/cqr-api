package com.zodus.template.api.controllers;

import com.zodus.template.api.dtos.requests.CreateOrderRequest;
import com.zodus.template.api.dtos.requests.UpdateOrderRequest;
import com.zodus.template.api.dtos.responses.OrderResponse;
import com.zodus.template.api.mappers.OrderMapper;
import com.zodus.template.application.services.OrderService;
import com.zodus.template.domain.models.Order;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final OrderMapper orderMapper;

  @PostMapping
  public ResponseEntity<OrderResponse> create(@RequestBody @Valid CreateOrderRequest request) {
    Order order = orderMapper.createOrderRequestToOrder(request);
    Order savedOrder = orderService.save(order);
    OrderResponse response = orderMapper.orderToOrderResponse(savedOrder);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> findById(@PathVariable UUID id) {
    Order order = orderService.findById(id);
    OrderResponse response = orderMapper.orderToOrderResponse(order);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Page<OrderResponse>> findAll(
      @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<Order> orders = orderService.findAll(pageable);
    Page<OrderResponse> response = orders.map(orderMapper::orderToOrderResponse);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponse> update(@PathVariable UUID id, 
                                             @RequestBody @Valid UpdateOrderRequest request) {
    Order orderDetails = orderMapper.updateOrderRequestToOrder(request);
    Order updatedOrder = orderService.update(id, orderDetails);
    OrderResponse response = orderMapper.orderToOrderResponse(updatedOrder);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
    orderService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
