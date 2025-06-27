package com.unb.fair_management_system.fair.update;

import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateFairHandler implements Handler<UpdateFairRequest, Fair> {

  private final FairRepository fairRepository;

  @Override
  public ResponseEntity<Fair> handle(final UpdateFairRequest request) {
    final Fair fair =
        fairRepository
            .findById(request.id())
            .orElseThrow(() -> new EntityNotFoundException("Fair not found: " + request.id()));

    fair.setName(request.name());
    fair.setDescription(request.description());
    fair.setStartDate(request.startDate());
    fair.setEndDate(request.endDate());
    fair.setLocation(request.location());
    fair.setCity(request.city());
    fair.setState(request.state());
    fair.setCreatedBy(request.updatedBy());

    final Fair updated = fairRepository.save(fair);
    return ResponseEntity.ok(updated);
  }
}
