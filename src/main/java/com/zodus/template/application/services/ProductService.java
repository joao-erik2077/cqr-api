package com.zodus.template.application.services;

import com.zodus.template.domain.models.Product;
import com.zodus.template.domain.repositories.ProductRepository;
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
public class ProductService {
  private final ProductRepository repository;

  public Product save(Product product) {
    if (product.getId() != null) {
      product.setUpdatedAt(LocalDateTime.now());
    }
    return repository.save(product);
  }

  public Product findById(UUID id) throws ResponseStatusException {
    return repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: " + id));
  }

  public Page<Product> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Page<Product> findAll(Pageable pageable, Specification<Product> specification) {
    return repository.findAll(specification, pageable);
  }

  public void deleteById(UUID id) throws ResponseStatusException {
    if (!repository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: " + id);
    }
    repository.deleteById(id);
  }

  public Product update(UUID id, Product productDetails) throws ResponseStatusException {
    Product existingProduct = findById(id);
    
    if (productDetails.getName() != null) {
      existingProduct.setName(productDetails.getName());
    }
    
    if (productDetails.getDescription() != null) {
      existingProduct.setDescription(productDetails.getDescription());
    }
    
    if (productDetails.getPrice() != null) {
      existingProduct.setPrice(productDetails.getPrice());
    }
    
    if (productDetails.getImageUrl() != null) {
      existingProduct.setImageUrl(productDetails.getImageUrl());
    }
    
    if (productDetails.getCategories() != null) {
      existingProduct.setCategories(productDetails.getCategories());
    }
    
    if (productDetails.getOrders() != null) {
      existingProduct.setOrders(productDetails.getOrders());
    }
    
    existingProduct.setUpdatedAt(LocalDateTime.now());
    return repository.save(existingProduct);
  }

  public boolean existsById(UUID id) {
    return repository.existsById(id);
  }

  public long count() {
    return repository.count();
  }
}
