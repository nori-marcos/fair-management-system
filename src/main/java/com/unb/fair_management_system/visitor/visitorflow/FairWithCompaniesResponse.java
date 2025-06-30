package com.unb.fair_management_system.visitor.visitorflow;

import com.unb.fair_management_system.fair.Fair;

import java.util.List;

public record FairWithCompaniesResponse(
    Fair fairDetails,
    List<CompanyInFairResponse> companies,
    boolean isSubscribedToFair
) {}