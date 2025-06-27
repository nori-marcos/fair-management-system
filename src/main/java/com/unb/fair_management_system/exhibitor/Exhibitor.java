package com.unb.fair_management_system.exhibitor;

import com.unb.fair_management_system.authentication.user.User;
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
@Table(
    name = "exhibitors",
    uniqueConstraints = @UniqueConstraint(columnNames = {"company_id", "fair_id"}))
public class Exhibitor {
  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private User user;

  @Column(nullable = false)
  private String contactName;

  @Column(nullable = false)
  private String contactEmail;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "company_id", nullable = false)
  private Company company;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "fair_id", nullable = false)
  private Fair fair;

  @OneToMany(mappedBy = "exhibitor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Product> products = new ArrayList<>();

  @Column(nullable = false)
  private String createdBy;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }

  public Exhibitor(final Company company, final Fair fair, final String createdBy) {
    this.company = company;
    this.fair = fair;
    this.createdBy = createdBy;
  }
}
