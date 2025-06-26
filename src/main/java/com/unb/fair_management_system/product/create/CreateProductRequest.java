package com.unb.fair_management_system.product.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateProductRequest(
    @NotBlank String name,
    @NotBlank String description,
    @NotNull @PositiveOrZero BigDecimal price,
    @NotNull String createdBy) {}
