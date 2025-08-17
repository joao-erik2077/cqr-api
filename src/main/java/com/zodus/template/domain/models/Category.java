package com.zodus.template.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_category")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt;

  @ManyToMany(mappedBy = "categories")
  private Set<Product> products = new HashSet<>();
}
