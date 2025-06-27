package com.unb.fair_management_system.ticket;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.visitor.Visitor;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
  private final TicketRepository ticketRepository;

  public TicketService(final TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public Ticket createForVisitor(final Visitor visitor) {
    final Ticket ticket = new Ticket(visitor);
    return ticketRepository.save(ticket);
  }

  public Ticket createForExhibitor(final Exhibitor exhibitor) {
    final Ticket ticket = new Ticket(exhibitor);
    return ticketRepository.save(ticket);
  }
}
