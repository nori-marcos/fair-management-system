package com.unb.fair_management_system.company.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateCompanyRequest(
    @NotNull UUID id,
    @NotBlank String name,
    @NotBlank String email,
    @NotBlank String phone,
    @NotBlank String cnpj,
    @NotBlank String updatedBy) {}
