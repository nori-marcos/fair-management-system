package com.unb.fair_management_system.exhibitor.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateExhibitorRequest(
    @Schema(example = "João da Silva") @NotNull String contactName,
    @Schema(example = "joao.silva@empresa.com") @NotNull String contactEmail,
    @Schema(example = "00000006-0000-0000-0000-000000000001") @NotNull UUID companyId,
    @Schema(example = "00000001-0000-0000-0000-000000000001") @NotNull UUID fairId,
    @Schema(example = "system") @NotNull String createdBy,
    @Schema(example = "00000003-0000-0000-0000-000000000002") @NotNull UUID userId) {}
