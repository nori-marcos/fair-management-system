package com.unb.fair_management_system.ticket;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.visitor.Visitor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Visitor visitor;

  @OneToOne
  @JoinColumn(name = "exhibitor_id", unique = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Exhibitor exhibitor;

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
