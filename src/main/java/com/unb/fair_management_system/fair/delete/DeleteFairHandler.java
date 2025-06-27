package com.unb.fair_management_system.fair.delete;

import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteFairHandler implements Handler<UUID, Void> {

  private final FairRepository fairRepository;

  @Override
  public ResponseEntity<Void> handle(UUID id) {
    if (!fairRepository.existsById(id)) {
      throw new EntityNotFoundException("Fair not found: " + id);
    }
    fairRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
