package com.zodus.template.api.mappers;

import com.zodus.template.api.dtos.requests.CreateProductRequest;
import com.zodus.template.api.dtos.requests.UpdateProductRequest;
import com.zodus.template.api.dtos.responses.ProductResponse;
import com.zodus.template.application.services.CategoryService;
import com.zodus.template.domain.models.Category;
import com.zodus.template.domain.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductMapper {
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;
  
  public Product createProductRequestToProduct(CreateProductRequest request) {
    Set<Category> categories = request.categories().stream().map(
        categoryService::findById
    ).collect(Collectors.toSet());

    Product product = new Product();
    product.setName(request.name());
    product.setDescription(request.description());
    product.setPrice(request.price());
    product.setImageUrl(request.imageUrl());
    product.setCategories(categories);
    return product;
  }

  public Product updateProductRequestToProduct(UpdateProductRequest request) {
    Product product = new Product();
    if (request.name() != null) {
      product.setName(request.name());
    }
    if (request.description() != null) {
      product.setDescription(request.description());
    }
    if (request.price() != null) {
      product.setPrice(request.price());
    }
    if (request.imageUrl() != null) {
      product.setImageUrl(request.imageUrl());
    }
    if (request.categories() != null && !request.categories().isEmpty()) {
      Set<Category> categories = request.categories().stream().map(
          categoryService::findById
      ).collect(Collectors.toSet());
      product.setCategories(categories);
    }
    return product;
  }

  public ProductResponse productToProductResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getImageUrl(),
        product.getCategories().stream().map(
            categoryMapper::categoryToCategoryResponse
        ).toList(),
        product.getCreatedAt(),
        product.getUpdatedAt()
    );
  }
}
