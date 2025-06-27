package com.unb.fair_management_system.company.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateCompanyRequest(
    @Schema(example = "Tech Solutions Ltda.") @NotBlank String name,
    @Schema(example = "empresa@techsolutions.com") @NotBlank @Email String email,
    @Schema(example = "+55 11 91234-5678") @NotBlank String phone,
    @Schema(example = "12.345.678/0001-90") @NotBlank String cnpj,
    @Schema(example = "EXHIBITOR") @NotBlank String createdBy) {}
