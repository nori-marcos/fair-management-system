package com.unb.fair_management_system.product.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductRequest(
    @NotNull @Schema(example = "00000008-0000-0000-0000-000000000001") UUID id,
    @NotBlank @Schema(example = "Notebook Ultra X") String name,
    @NotBlank @Schema(example = "Notebook Ryzen 9, 32GB RAM, SSD 1TB e GPU RTX 4060")
        String description,
    @PositiveOrZero @Schema(example = "8999.9") BigDecimal price,
    @NotNull @Schema(example = "system") String updatedBy) {}
