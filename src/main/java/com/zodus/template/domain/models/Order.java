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
@Table(name = "_order")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt;

  @ManyToMany
  @JoinTable(
      name = "_order_product",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private List<Product> products = new ArrayList<>();

  @OneToOne
  @JoinColumn(name = "sales_record_id")
  private SalesRecord salesRecord;
}
