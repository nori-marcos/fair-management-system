package com.unb.fair_management_system.product.list;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
    UUID id, String name, String description, BigDecimal price, UUID companyId) {}
