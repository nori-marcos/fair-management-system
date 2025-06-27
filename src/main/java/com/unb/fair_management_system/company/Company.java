package com.unb.fair_management_system.company;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company {
  @Id @GeneratedValue private UUID id;

  @NotBlank private String name;

  @NotBlank private String contactName;

  @NotBlank private String email;

  @NotBlank private String phone;

  @NotBlank private String cnpj;

  @NotBlank private String createdBy;

  @NotNull private LocalDateTime createdDate;

  @OneToMany(mappedBy = "company")
  private List<Exhibitor> exhibitors = new ArrayList<>();

  public Company(
      final String name,
      final String email,
      final String phone,
      final String cnpj,
      final String createdBy) {
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.cnpj = cnpj;
    this.createdBy = createdBy;
    this.createdDate = LocalDateTime.now();
  }

  @PrePersist
  public void prePersist() {
    this.createdDate = LocalDateTime.now();
  }
}
