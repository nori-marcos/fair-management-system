package com.unb.fair_management_system.fair;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fairs")
public class Fair {
  @Id @GeneratedValue private UUID id;
  @NotBlank private String name;
  @NotBlank private String description;
  @NotNull private LocalDate startDate;
  @NotNull private LocalDate endDate;
  @NotBlank private String location;
  @NotBlank private String city;
  @NotBlank private String state;
  @NotBlank private String createdBy;
  @NotNull private LocalDate createDate;

  @PrePersist
  public void prePersist() {
    this.createDate = LocalDate.now();
  }

  public Fair(
      final String name,
      final String description,
      final LocalDate startDate,
      final LocalDate endDate,
      final String location,
      final String city,
      final String state,
      final String createdBy) {
    this.name = name;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.location = location;
    this.city = city;
    this.state = state;
    this.createdBy = createdBy;
  }
}
