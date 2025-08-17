package com.zodus.template.api.controllers;

import com.zodus.template.api.dtos.requests.CreateProductRequest;
import com.zodus.template.api.dtos.requests.UpdateProductRequest;
import com.zodus.template.api.dtos.responses.ProductResponse;
import com.zodus.template.api.mappers.ProductMapper;
import com.zodus.template.application.services.ProductService;
import com.zodus.template.domain.models.Product;
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
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
  private final ProductService productService;
  private final ProductMapper productMapper;

  @PostMapping
  public ResponseEntity<ProductResponse> create(@RequestBody @Valid CreateProductRequest request) {
    Product product = productMapper.createProductRequestToProduct(request);
    Product savedProduct = productService.save(product);
    ProductResponse response = productMapper.productToProductResponse(savedProduct);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) {
    Product product = productService.findById(id);
    ProductResponse response = productMapper.productToProductResponse(product);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Page<ProductResponse>> findAll(
      @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<Product> products = productService.findAll(pageable);
    Page<ProductResponse> response = products.map(productMapper::productToProductResponse);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> update(@PathVariable UUID id, 
                                               @RequestBody @Valid UpdateProductRequest request) {
    Product productDetails = productMapper.updateProductRequestToProduct(request);
    Product updatedProduct = productService.update(id, productDetails);
    ProductResponse response = productMapper.productToProductResponse(updatedProduct);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
    productService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
