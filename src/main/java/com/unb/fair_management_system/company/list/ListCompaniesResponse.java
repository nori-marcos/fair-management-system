package com.unb.fair_management_system.company.list;

import com.unb.fair_management_system.fair.Fair;
import java.util.List;
import java.util.UUID;

public record ListCompaniesResponse(
    UUID id, String name, String email, String phone, String cnpj, List<Fair> fairs) {}
