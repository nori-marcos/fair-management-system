package com.unb.fair_management_system.visitor;

import com.unb.fair_management_system.fair.Fair;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visitors")
public class Visitor {
  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private String contactName;

  @Column(nullable = false, unique = true)
  private String contactEmail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fair_id", nullable = false)
  private Fair fair;

  private String createdBy;
  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
  }

  public Visitor(
      final String contactName,
      final String contactEmail,
      final Fair fair,
      final String createdBy) {
    this.contactName = contactName;
    this.contactEmail = contactEmail;
    this.fair = fair;
    this.createdBy = createdBy;
  }
}
