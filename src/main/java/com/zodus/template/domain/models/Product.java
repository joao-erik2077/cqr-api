package com.zodus.template.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;
  private String description;
  private String price;
  private String imageUrl;

  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt;

  @ManyToMany(mappedBy = "products")
  private List<Category> categories = new ArrayList<>();

  @ManyToMany(mappedBy = "products")
  private List<Order> orders = new ArrayList<>();
}
