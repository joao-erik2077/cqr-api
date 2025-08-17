package com.zodus.template.api.mappers;

import com.zodus.template.api.dtos.requests.CreateCategoryRequest;
import com.zodus.template.api.dtos.requests.UpdateCategoryRequest;
import com.zodus.template.api.dtos.responses.CategoryResponse;
import com.zodus.template.domain.models.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {
  
  public Category createCategoryRequestToCategory(CreateCategoryRequest request) {
    Category category = new Category();
    category.setName(request.name());
    return category;
  }

  public Category updateCategoryRequestToCategory(UpdateCategoryRequest request) {
    Category category = new Category();
    if (request.name() != null) {
      category.setName(request.name());
    }
    return category;
  }

  public CategoryResponse categoryToCategoryResponse(Category category) {
    return new CategoryResponse(
        category.getId(),
        category.getName(),
        category.getCreatedAt(),
        category.getUpdatedAt()
    );
  }
}
