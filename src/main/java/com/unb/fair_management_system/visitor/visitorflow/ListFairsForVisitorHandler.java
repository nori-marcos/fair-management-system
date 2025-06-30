package com.unb.fair_management_system.visitor.visitorflow;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import com.unb.fair_management_system.visitor.VisitorRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListFairsForVisitorHandler implements Handler<UUID, List<FairWithCompaniesResponse>> {

  private final FairRepository fairRepository;
  private final ExhibitorRepository exhibitorRepository;
  private final VisitorRepository visitorRepository;

  @Override
  public ResponseEntity<List<FairWithCompaniesResponse>> handle(final UUID userId) {
    final List<Fair> allFairs = fairRepository.findAll();

    final List<FairWithCompaniesResponse> responseList =
        allFairs.stream()
            .map(
                fair -> {
                  final List<CompanyInFairResponse> companies =
                      exhibitorRepository.findByFairId(fair.getId()).stream()
                          .map(Exhibitor::getCompany)
                          .distinct()
                          .map(
                              company ->
                                  new CompanyInFairResponse(
                                      company.getId(),
                                      company.getName(),
                                      company.getEmail(),
                                      company.getPhone()))
                          .toList();

                  return new FairWithCompaniesResponse(
                      fair,
                      companies,
                      visitorRepository.existsByUserIdAndFairId(userId, fair.getId()));
                })
            .toList();

    return ResponseEntity.ok(responseList);
  }
}
