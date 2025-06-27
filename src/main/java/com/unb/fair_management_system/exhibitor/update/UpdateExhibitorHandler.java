package com.unb.fair_management_system.exhibitor.update;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateExhibitorHandler
    implements Handler<UpdateExhibitorRequest, UpdateExhibitorResponse> {

  private final ExhibitorRepository exhibitorRepository;

  @Override
  public ResponseEntity<UpdateExhibitorResponse> handle(final UpdateExhibitorRequest request) {
    final Exhibitor exhibitor =
        exhibitorRepository
            .findById(request.id())
            .orElseThrow(() -> new EntityNotFoundException("Exhibitor not found: " + request.id()));

    exhibitor.setContactName(request.contactName());
    exhibitor.setContactEmail(request.contactEmail());
    exhibitor.setCreatedBy(request.updatedBy());

    final Exhibitor updated = exhibitorRepository.save(exhibitor);
    final UpdateExhibitorResponse updatedResponse =
        new UpdateExhibitorResponse(
            updated.getId(),
            updated.getContactName(),
            updated.getContactEmail(),
            updated.getCompany().getName());
    return ResponseEntity.ok(updatedResponse);
  }
}
