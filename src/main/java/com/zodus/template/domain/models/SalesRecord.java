package com.zodus.template.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_sales_record")
public class SalesRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt;

  @OneToOne
  @JoinColumn(name = "order_id")
  private Order order;
}
