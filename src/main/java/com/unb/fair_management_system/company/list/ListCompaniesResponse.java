package com.unb.fair_management_system.company.list;

import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.product.ProductResponse;
import java.util.List;
import java.util.UUID;

public record ListCompaniesResponse(
    UUID id,
    String name,
    String email,
    String phone,
    String cnpj,
    List<Fair> fairs,
    List<ProductResponse> products) {}
