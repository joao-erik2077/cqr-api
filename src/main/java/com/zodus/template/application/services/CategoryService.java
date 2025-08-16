package com.zodus.template.application.services;

import com.zodus.template.domain.models.Category;
import com.zodus.template.domain.repositories.CategoryRepository;
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
public class CategoryService {
  private final CategoryRepository repository;

  public Category save(Category category) {
    if (category.getId() != null) {
      category.setUpdatedAt(LocalDateTime.now());
    }
    return repository.save(category);
  }

  public Category findById(UUID id) throws ResponseStatusException {
    return repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found for id: " + id));
  }

  public Page<Category> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Page<Category> findAll(Pageable pageable, Specification<Category> specification) {
    return repository.findAll(specification, pageable);
  }

  public void deleteById(UUID id) throws ResponseStatusException {
    if (!repository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found for id: " + id);
    }
    repository.deleteById(id);
  }

  public Category update(UUID id, Category categoryDetails) throws ResponseStatusException {
    Category existingCategory = findById(id);
    
    if (categoryDetails.getName() != null) {
      existingCategory.setName(categoryDetails.getName());
    }
    
    existingCategory.setUpdatedAt(LocalDateTime.now());
    return repository.save(existingCategory);
  }

  public boolean existsById(UUID id) {
    return repository.existsById(id);
  }

  public long count() {
    return repository.count();
  }
}
