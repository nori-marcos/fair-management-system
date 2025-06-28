package com.unb.fair_management_system.visitor.update;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record UpdateVisitorRequest(
    @Schema(example = "00000004-0000-0000-0000-000000000001") UUID id,
    @Schema(example = "Carol Visitor") String contactName,
    @Schema(example = "carol@visitor.com") String contactEmail,
    @Schema(example = "system") String updatedBy) {}
