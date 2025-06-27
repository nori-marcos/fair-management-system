package com.unb.fair_management_system.exhibitor.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateExhibitorRequest(
    @Schema(example = "00000007-0000-0000-0000-000000000001") @NotNull UUID id,
    @Schema(example = "Elias Expositor") @NotBlank String contactName,
    @Schema(example = "elias@techcorp.com") @NotBlank @Email String contactEmail,
    @Schema(example = "system") @NotBlank String updatedBy) {}
