package com.unb.fair_management_system.exhibitor.delete;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import com.unb.fair_management_system.ticket.Ticket;
import com.unb.fair_management_system.ticket.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteExhibitorHandler implements Handler<UUID, Void> {

  private final ExhibitorRepository exhibitorRepository;
  private final TicketRepository ticketRepository;

  @Override
  public ResponseEntity<Void> handle(final UUID id) {
    final Exhibitor exhibitor =
        exhibitorRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Exhibitor not found: " + id));

    final Optional<Ticket> ticket = ticketRepository.findByExhibitor(exhibitor);
    ticket.ifPresent(ticketRepository::delete);

    exhibitorRepository.delete(exhibitor);
    return ResponseEntity.noContent().build();
  }
}
