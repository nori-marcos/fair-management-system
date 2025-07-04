package com.unb.fair_management_system.visitor;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.ticket.Ticket;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "visitors",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "fair_id"}))
public class Visitor {
  @Id @GeneratedValue private UUID id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String contactName;

  @Column(nullable = false)
  private String contactEmail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fair_id", nullable = false)
  private Fair fair;

  @OneToOne(mappedBy = "visitor", cascade = CascadeType.REMOVE, orphanRemoval = true)
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

  public Visitor(
      final User user,
      final String contactName,
      final String contactEmail,
      final Fair fair,
      final String createdBy) {
    this.user = user;
    this.contactName = contactName;
    this.contactEmail = contactEmail;
    this.fair = fair;
    this.createdBy = createdBy;
  }
}
