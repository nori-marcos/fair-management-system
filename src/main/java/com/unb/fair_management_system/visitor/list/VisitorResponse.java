package com.unb.fair_management_system.visitor.list;

import java.util.UUID;

public record VisitorResponse(UUID id, String contactName, String contactEmail, String fairName) {}
