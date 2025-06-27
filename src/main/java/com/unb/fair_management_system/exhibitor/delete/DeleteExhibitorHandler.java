package com.unb.fair_management_system.exhibitor.delete;

import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteExhibitorHandler implements Handler<UUID, Void> {

  private final ExhibitorRepository exhibitorRepository;

  @Override
  public ResponseEntity<Void> handle(final UUID id) {
    if (!exhibitorRepository.existsById(id)) {
      throw new EntityNotFoundException("Exhibitor not found: " + id);
    }
    exhibitorRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
