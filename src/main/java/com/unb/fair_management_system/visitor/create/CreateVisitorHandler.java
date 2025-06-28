package com.unb.fair_management_system.visitor.create;

import com.unb.fair_management_system.authentication.role.Role;
import com.unb.fair_management_system.authentication.role.RoleRepository;
import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import com.unb.fair_management_system.ticket.Ticket;
import com.unb.fair_management_system.ticket.TicketRepository;
import com.unb.fair_management_system.ticket.TicketResponse;
import com.unb.fair_management_system.visitor.Visitor;
import com.unb.fair_management_system.visitor.VisitorRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateVisitorHandler implements Handler<CreateVisitorRequest, TicketResponse> {

  private final VisitorRepository visitorRepository;
  private final FairRepository fairRepository;
  private final TicketRepository ticketRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public ResponseEntity<TicketResponse> handle(final CreateVisitorRequest request) {
    final Fair fair =
        fairRepository
            .findById(request.fairId())
            .orElseThrow(() -> new EntityNotFoundException("Fair not found"));

    final User user =
        userRepository
            .findById(request.userId())
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.userId()));

    updateUserRoles(user);

    final Visitor visitor =
        new Visitor(user, request.contactName(), request.contactEmail(), fair, request.createdBy());

    final Ticket ticket = new Ticket(visitor);
    visitorRepository.save(visitor);
    final Ticket issuedTicket = ticketRepository.save(ticket);

    final TicketResponse response =
        new TicketResponse(
            issuedTicket.getId(),
            fair.getName(),
            visitor.getContactName(),
            null,
            ticket.getCreatedAt());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  private void updateUserRoles(final User user) {
    final Role role =
        roleRepository
            .findByName("VISITOR")
            .orElseThrow(() -> new EntityNotFoundException("Role not found: " + "VISITOR"));
    final Set<Role> roles = user.getRoles();
    roles.add(role);
    user.setRoles(roles);
    userRepository.save(user);
  }
}
