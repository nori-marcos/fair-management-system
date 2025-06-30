package com.unb.fair_management_system.exhibitor.fairStatus;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExhibitorFairStatusHandler
    implements Handler<UUID, ExhibitorFairStatusResponse> {

  private final ExhibitorRepository exhibitorRepository;
  private final FairRepository fairRepository;

  @Override
  public ResponseEntity<ExhibitorFairStatusResponse> handle(final UUID userId) {
    final List<Fair> allFairs = fairRepository.findAll();

    final List<Exhibitor> userExhibitors = exhibitorRepository.findByUserId(userId);
    final List<Fair> subscribedFairs = userExhibitors.stream().map(Exhibitor::getFair).toList();

    final List<Fair> unsubscribedFairs =
        allFairs.stream().filter(fair -> !subscribedFairs.contains(fair)).toList();

    final ExhibitorFairStatusResponse response =
        new ExhibitorFairStatusResponse(subscribedFairs, unsubscribedFairs);

    return ResponseEntity.ok(response);
  }
}
