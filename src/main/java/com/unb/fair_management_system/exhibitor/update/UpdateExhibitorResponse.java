package com.unb.fair_management_system.exhibitor.update;

import java.util.UUID;

public record UpdateExhibitorResponse(
    UUID id, String contactName, String contactEmail, String companyName) {}
