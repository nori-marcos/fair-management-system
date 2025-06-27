package com.unb.fair_management_system.fair.create;

import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateFairHandler implements Handler<CreateFairRequest, UUID> {

  private final FairRepository fairRepository;

  @Override
  public ResponseEntity<UUID> handle(final CreateFairRequest request) {
    final Fair fair =
        new Fair(
            request.name(),
            request.description(),
            request.startDate(),
            request.endDate(),
            request.location(),
            request.city(),
            request.state(),
            request.createdBy());

    final UUID id = fairRepository.save(fair).getId();
    return ResponseEntity.status(HttpStatus.CREATED).body(id);
  }
}
