package com.zodus.template.application.services;

import com.zodus.template.domain.models.Order;
import com.zodus.template.domain.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
  private final OrderRepository repository;

  public Order save(Order order) {
    if (order.getId() != null) {
      order.setUpdatedAt(LocalDateTime.now());
    }
    return repository.save(order);
  }

  public Order findById(UUID id) throws ResponseStatusException {
    return repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for id: " + id));
  }

  public Page<Order> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Page<Order> findAll(Pageable pageable, Specification<Order> specification) {
    return repository.findAll(specification, pageable);
  }

  public void deleteById(UUID id) throws ResponseStatusException {
    if (!repository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for id: " + id);
    }
    repository.deleteById(id);
  }

  public Order update(UUID id, Order orderDetails) throws ResponseStatusException {
    Order existingOrder = findById(id);
    
    if (orderDetails.getProducts() != null) {
      existingOrder.setProducts(orderDetails.getProducts());
    }
    
    if (orderDetails.getSalesRecord() != null) {
      existingOrder.setSalesRecord(orderDetails.getSalesRecord());
    }
    
    existingOrder.setUpdatedAt(LocalDateTime.now());
    return repository.save(existingOrder);
  }

  public boolean existsById(UUID id) {
    return repository.existsById(id);
  }

  public long count() {
    return repository.count();
  }
}
