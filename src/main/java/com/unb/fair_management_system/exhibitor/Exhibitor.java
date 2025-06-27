package com.unb.fair_management_system.exhibitor;

import com.unb.fair_management_system.company.Company;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.product.Product;
import jakarta.persistence.*;
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
@Table(name = "exhibitors")
public class Exhibitor {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private String contactName;

  @Column(nullable = false, unique = true)
  private String contactEmail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fair_id", nullable = false)
  private Fair fair;

  @OneToMany(mappedBy = "exhibitor")
  private List<Product> products = new ArrayList<>();

  private String createdBy;
  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
  }

  public Exhibitor(final Company company, final Fair fair, final String createdBy) {
    this.company = company;
    this.fair = fair;
    this.createdBy = createdBy;
  }
}
