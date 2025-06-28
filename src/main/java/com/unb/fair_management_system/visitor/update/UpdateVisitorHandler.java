package com.unb.fair_management_system.visitor.update;

import com.unb.fair_management_system.starter.mediator.Handler;
import com.unb.fair_management_system.visitor.Visitor;
import com.unb.fair_management_system.visitor.VisitorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateVisitorHandler implements Handler<UpdateVisitorRequest, UpdateVisitorResponse> {

  private final VisitorRepository visitorRepository;

  @Override
  public ResponseEntity<UpdateVisitorResponse> handle(final UpdateVisitorRequest request) {
    final Visitor visitor =
        visitorRepository
            .findById(request.id())
            .orElseThrow(() -> new EntityNotFoundException("Visitor not found: " + request.id()));

    visitor.setContactName(request.contactName());
    visitor.setContactEmail(request.contactEmail());
    visitor.setCreatedBy(request.updatedBy());

    final Visitor updatedVisitor = visitorRepository.save(visitor);

    final UpdateVisitorResponse response =
        new UpdateVisitorResponse(
            updatedVisitor.getId(),
            updatedVisitor.getContactName(),
            updatedVisitor.getContactEmail(),
            updatedVisitor.getFair().getName());

    return ResponseEntity.ok().body(response);
  }
}
