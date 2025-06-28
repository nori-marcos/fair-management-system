package com.unb.fair_management_system.visitor.update;

import java.util.UUID;

public record UpdateVisitorResponse(UUID visitorId, String contactName, String contactEmail, String fairName) {}
