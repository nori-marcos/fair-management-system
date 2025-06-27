package com.unb.fair_management_system.company.list;

import java.util.UUID;

public record ListCompaniesResponse(
    UUID id, String name, String email, String phone, String cnpj) {}
