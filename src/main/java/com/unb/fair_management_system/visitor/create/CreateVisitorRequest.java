package com.unb.fair_management_system.visitor.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateVisitorRequest(
    @Schema(example = "Joana Silva") @NotBlank String contactName,
    @Schema(example = "joana.silva@email.com") @NotBlank @Email String contactEmail,
    @Schema(example = "42d4c461-ec1b-43c3-9869-4b1daf5fa7dd") @NotNull UUID fairId,
    @Schema(example = "system") @NotBlank String createdBy,
    @Schema(example = "00000003-0000-0000-0000-000000000001") @NotNull UUID userId) {}
