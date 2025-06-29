package com.unb.fair_management_system.authentication.user;

import com.unb.fair_management_system.authentication.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false)
  private String fullName;

  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new HashSet<>();

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

  public User(
      final String fullName,
      final String email,
      final String password,
      final Set<Role> roles,
      final String createdBy) {
    this.fullName = fullName;
    this.email = email;
    this.password = password;
    this.roles = roles;
    this.createdBy = createdBy;
  }
}
