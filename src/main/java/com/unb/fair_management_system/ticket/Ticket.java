package com.unb.fair_management_system.ticket;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.visitor.Visitor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
  @Id @GeneratedValue private UUID id;

  @ManyToOne
  @JoinColumn(name = "fair_id", nullable = false)
  private Fair fair;

  @OneToOne
  @JoinColumn(name = "visitor_id", unique = true)
  private Visitor visitor;

  @OneToOne
  @JoinColumn(name = "exhibitor_id", unique = true)
  private Exhibitor exhibitor;

  private String createdBy;
  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
  }

  public Ticket(final Visitor visitor) {
    this.visitor = visitor;
    this.fair = visitor.getFair();
    this.createdBy = visitor.getCreatedBy();
  }

  public Ticket(final Exhibitor exhibitor) {
    this.exhibitor = exhibitor;
    this.fair = exhibitor.getFair();
    this.createdBy = exhibitor.getCreatedBy();
  }
}
