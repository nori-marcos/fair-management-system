package com.unb.fair_management_system.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
  @NotNull
  @DecimalMin(value = "0.0")
  private BigDecimal price;
  @NotNull
  private LocalDateTime createDate;
  @NotNull
  private String createdBy;
}



