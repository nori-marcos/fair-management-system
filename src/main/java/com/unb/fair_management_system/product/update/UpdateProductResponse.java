package com.unb.fair_management_system.product.update;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductResponse(
    UUID id, String name, String description, BigDecimal price, UUID exhibitorId) {}
