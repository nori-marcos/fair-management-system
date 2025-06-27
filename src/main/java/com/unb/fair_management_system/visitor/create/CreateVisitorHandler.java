package com.unb.fair_management_system.visitor.create;

import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import com.unb.fair_management_system.ticket.Ticket;
import com.unb.fair_management_system.ticket.TicketRepository;
import com.unb.fair_management_system.visitor.Visitor;
import com.unb.fair_management_system.visitor.VisitorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateVisitorHandler implements Handler<CreateVisitorRequest, Ticket> {

  private final VisitorRepository visitorRepository;
  private final FairRepository fairRepository;
  private final TicketRepository ticketRepository;

  @Override
  public ResponseEntity<Ticket> handle(final CreateVisitorRequest request) {
    final Fair fair =
        fairRepository
            .findById(request.fairId())
            .orElseThrow(() -> new EntityNotFoundException("Fair not found"));

    final Visitor visitor = new Visitor(request.contactName(), request.contactEmail(), fair, request.createdBy());
    final Ticket ticket = new Ticket(visitor);
    visitorRepository.save(visitor);
    final Ticket issuedTicket = ticketRepository.save(ticket);
    return ResponseEntity.status(HttpStatus.CREATED).body(issuedTicket);
  }
}
