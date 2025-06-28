package com.unb.fair_management_system.company;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String cnpj;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Exhibitor> exhibitors = new ArrayList<>();

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Product> products = new ArrayList<>();

  @Column(nullable = false)
  private String createdBy;

  @Column(nullable = false)
  private LocalDateTime createdAt;

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
  }

  @PrePersist
  public void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }

  public List<Fair> getFairs() {
    final List<Fair> fairs = new ArrayList<>();
    for (final Exhibitor exhibitor : exhibitors) {
      if (!fairs.contains(exhibitor.getFair())) {
        fairs.add(exhibitor.getFair());
      }
    }
    return fairs;
  }
}
