package com.unb.fair_management_system.exhibitor.list;

import com.unb.fair_management_system.product.list.ProductResponse;
import java.util.List;
import java.util.UUID;

public record ExhibitorResponse(
    UUID id,
    String contactName,
    String contactEmail,
    String companyName,
    List<ProductResponse> products) {}
