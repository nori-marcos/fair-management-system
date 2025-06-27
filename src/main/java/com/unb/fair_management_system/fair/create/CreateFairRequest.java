package com.unb.fair_management_system.fair.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateFairRequest(
    @Schema(example = "Feira de Tecnologia 2025") @NotBlank String name,
    @Schema(example = "Feira com foco em inovação e tecnologia.") @NotBlank String description,
    @Schema(example = "2025-10-01") @FutureOrPresent @NotNull LocalDate startDate,
    @Schema(example = "2025-10-03") @FutureOrPresent @NotNull LocalDate endDate,
    @Schema(example = "Centro de Convenções Ulysses Guimarães") @NotBlank String location,
    @Schema(example = "Brasília") @NotBlank String city,
    @Schema(example = "DF") @NotBlank String state,
    @Schema(example = "system") @NotBlank String createdBy) {}
