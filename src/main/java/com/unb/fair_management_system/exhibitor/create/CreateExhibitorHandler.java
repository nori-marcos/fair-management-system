package com.unb.fair_management_system.exhibitor.create;

import com.unb.fair_management_system.authentication.role.Role;
import com.unb.fair_management_system.authentication.role.RoleRepository;
import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.company.Company;
import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import com.unb.fair_management_system.ticket.Ticket;
import com.unb.fair_management_system.ticket.TicketRepository;
import com.unb.fair_management_system.ticket.TicketResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateExhibitorHandler implements Handler<CreateExhibitorRequest, TicketResponse> {

  private final ExhibitorRepository exhibitorRepository;
  private final CompanyRepository companyRepository;
  private final FairRepository fairRepository;
  private final TicketRepository ticketRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public ResponseEntity<TicketResponse> handle(final CreateExhibitorRequest request) {
    final Company company =
        companyRepository
            .findById(request.companyId())
            .orElseThrow(() -> new EntityNotFoundException("Company not found"));

    final Fair fair =
        fairRepository
            .findById(request.fairId())
            .orElseThrow(() -> new EntityNotFoundException("Fair not found"));

    final User user =
        userRepository
            .findById(request.userId())
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.userId()));

    updateUserRoles(user);

    final Exhibitor exhibitor =
        new Exhibitor(
            user,
            request.contactName(),
            request.contactEmail(),
            company,
            fair,
            request.createdBy());

    final Ticket ticket = new Ticket(exhibitor);
    exhibitorRepository.save(exhibitor);
    final Ticket issuedTicket = ticketRepository.save(ticket);

    final TicketResponse response =
        new TicketResponse(
            issuedTicket.getId(),
            fair.getName(),
            null,
            exhibitor.getContactName(),
            ticket.getCreatedAt());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  private void updateUserRoles(final User user) {
    final Role role =
        roleRepository
            .findByName("EXHIBITOR")
            .orElseThrow(() -> new EntityNotFoundException("Role not found: " + "VISITOR"));
    final Set<Role> roles = user.getRoles();
    roles.add(role);
    userRepository.save(user);
  }
}
