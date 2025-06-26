package com.unb.fair_management_system.exhibitor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exhibitors")
public class Exhibitor {
  @Id
  @GeneratedValue
  private UUID id;
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NotBlank
  @Email
  private String contactEmail;
  @NotBlank
  @Pattern(
      regexp = "^(\\+\\d{1,3}\\s?)?(\\(?\\d{2}\\)?\\s?)?\\d{4,5}[-\\s]?\\d{4}$",
      message = "Invalid phone number format"
  )
  private String contactPhoneNumber;
}
