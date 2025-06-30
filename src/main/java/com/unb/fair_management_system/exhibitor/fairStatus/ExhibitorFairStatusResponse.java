package com.unb.fair_management_system.exhibitor.fairStatus;

import com.unb.fair_management_system.fair.Fair;
import java.util.List;

public record ExhibitorFairStatusResponse(
    List<Fair> subscribedFairs, List<Fair> unsubscribedFairs) {}
