package com.unb.fair_management_system.visitor.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateVisitorRequest(
    @Schema(example = "Joana Silva") @NotBlank String contactName,
    @Schema(example = "joana.silva@email.com") @NotBlank @Email String contactEmail,
    @Schema(example = "b29f764d-d40b-445e-b74a-9f0e302c9bd1") @NotNull UUID fairId,
    @Schema(example = "VISITOR") @NotBlank String createdBy,
    @Schema(example = "e3c4a3f2-1d21-4c89-9e6f-a8d5f0d4c123") @NotBlank String userId) {}
