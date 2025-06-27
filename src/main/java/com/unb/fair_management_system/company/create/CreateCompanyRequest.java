package com.unb.fair_management_system.company.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCompanyRequest(
    @Schema(example = "Tech Solutions Ltda.") @NotBlank String name,
    @Schema(example = "empresa@techsolutions.com") @NotBlank @Email String email,
    @Schema(example = "6133221100") @NotBlank String phone,
    @Schema(example = "99999678000199") @NotBlank String cnpj,
    @Schema(example = "system") @NotBlank String createdBy) {}
