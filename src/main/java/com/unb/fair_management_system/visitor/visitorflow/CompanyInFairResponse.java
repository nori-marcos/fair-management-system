package com.unb.fair_management_system.visitor.visitorflow;

import java.util.UUID;

public record CompanyInFairResponse(UUID id, String name, String email, String phone) {}
