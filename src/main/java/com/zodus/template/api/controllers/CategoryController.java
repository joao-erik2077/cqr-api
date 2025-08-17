package com.zodus.template.api.controllers;

import com.zodus.template.api.dtos.requests.CreateCategoryRequest;
import com.zodus.template.api.dtos.requests.UpdateCategoryRequest;
import com.zodus.template.api.dtos.responses.CategoryResponse;
import com.zodus.template.api.mappers.CategoryMapper;
import com.zodus.template.application.services.CategoryService;
import com.zodus.template.domain.models.Category;
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
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @PostMapping
  public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request) {
    Category category = categoryMapper.createCategoryRequestToCategory(request);
    Category savedCategory = categoryService.save(category);
    CategoryResponse response = categoryMapper.categoryToCategoryResponse(savedCategory);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> findById(@PathVariable UUID id) {
    Category category = categoryService.findById(id);
    CategoryResponse response = categoryMapper.categoryToCategoryResponse(category);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Page<CategoryResponse>> findAll(
      @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<Category> categories = categoryService.findAll(pageable);
    Page<CategoryResponse> response = categories.map(categoryMapper::categoryToCategoryResponse);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> update(@PathVariable UUID id, 
                                                @RequestBody @Valid UpdateCategoryRequest request) {
    Category categoryDetails = categoryMapper.updateCategoryRequestToCategory(request);
    Category updatedCategory = categoryService.update(id, categoryDetails);
    CategoryResponse response = categoryMapper.categoryToCategoryResponse(updatedCategory);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
    categoryService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
