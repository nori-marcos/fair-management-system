package com.unb.fair_management_system.exhibitor;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.company.Company;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.ticket.Ticket;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "fair_id"}))
public class Exhibitor {
  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
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

  @OneToOne(mappedBy = "exhibitor", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private Ticket ticket;

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

  public Exhibitor(
      final User user,
      final String contactName,
      final String contactEmail,
      final Company company,
      final Fair fair,
      final String createdBy) {
    this.user = user;
    this.contactName = contactName;
    this.contactEmail = contactEmail;
    this.company = company;
    this.fair = fair;
    this.createdBy = createdBy;
  }
}
