package com.unb.fair_management_system.ticket;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
  Optional<Ticket> findByExhibitor(Exhibitor exhibitor);
}
