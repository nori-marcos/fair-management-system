package com.unb.fair_management_system.fair.get;

import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetFairHandler implements Handler<UUID, Fair> {

  private final FairRepository fairRepository;

  @Override
  public ResponseEntity<Fair> handle(final UUID uuid) {
    final Fair fair =
        fairRepository
            .findById(uuid)
            .orElseThrow(() -> new IllegalArgumentException("Fair not found"));
    return ResponseEntity.ok(fair);
  }
}
