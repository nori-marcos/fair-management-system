package com.unb.fair_management_system.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue
  private UUID id;
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NotNull @PositiveOrZero private BigDecimal price;
  @NotNull
  private LocalDateTime createDate;
  @NotNull
  private String createdBy;

  @PrePersist
  public void prePersist() {
    this.createDate = LocalDateTime.now();
  }

  public Product(
      final String name, final String description, final BigDecimal price, final String createdBy) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.createdBy = createdBy;
  }
}



