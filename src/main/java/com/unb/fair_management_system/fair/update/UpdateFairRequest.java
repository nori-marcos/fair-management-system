package com.unb.fair_management_system.fair.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateFairRequest(
    @Schema(example = "00000001-0000-0000-0000-000000000001") @NotNull UUID id,
    @Schema(example = "Feira de Tecnologia 2025") @NotBlank String name,
    @Schema(example = "Descrição mockada") @NotBlank String description,
    @Schema(example = "2025-10-01") @FutureOrPresent @NotNull LocalDate startDate,
    @Schema(example = "2025-10-03") @FutureOrPresent @NotNull LocalDate endDate,
    @Schema(example = "Centro XYZ") @NotBlank String location,
    @Schema(example = "Brasília") @NotBlank String city,
    @Schema(example = "DF") @NotBlank String state,
    @Schema(example = "system") @NotBlank String updatedBy) {}
