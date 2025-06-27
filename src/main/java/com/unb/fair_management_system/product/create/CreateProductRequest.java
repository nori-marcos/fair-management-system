package com.unb.fair_management_system.product.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(
    @Schema(example = "Sensor de Umidade Inteligente") @NotBlank String name,
    @Schema(
            example =
                "Sensor IoT com conectividade Wi-Fi para monitoramento em tempo real via aplicativo.")
        @NotBlank
        String description,
    @Schema(example = "199.99") @NotNull @PositiveOrZero BigDecimal price,
    @Schema(example = "e3c4a3f2-1d21-4c89-9e6f-a8d5f0d4c123") @NotNull UUID exhibitorId,
    @Schema(example = "tech_admin") @NotNull String createdBy) {}
