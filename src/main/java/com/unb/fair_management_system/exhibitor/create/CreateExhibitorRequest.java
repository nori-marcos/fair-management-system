package com.unb.fair_management_system.exhibitor.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateExhibitorRequest(
    @Schema(example = "João da Silva") @NotNull String contactName,
    @Schema(example = "joao.silva@empresa.com") @NotNull String contactEmail,
    @Schema(example = "b2f7f1c3-41dd-4a88-b10e-13b2d6e634df") @NotNull UUID companyId,
    @Schema(example = "a3d2f6c1-45f0-47df-8ec1-b749a69a9981") @NotNull UUID fairId,
    @Schema(example = "ADMIN") @NotNull String createdBy,
    @NotBlank String userId) {}
