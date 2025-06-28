package com.unb.fair_management_system.visitor.delete;

import com.unb.fair_management_system.authentication.role.Role;
import com.unb.fair_management_system.authentication.role.RoleRepository;
import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import com.unb.fair_management_system.visitor.Visitor;
import com.unb.fair_management_system.visitor.VisitorRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteVisitorHandler implements Handler<UUID, Void> {

  private final VisitorRepository visitorRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public ResponseEntity<Void> handle(final UUID id) {
    final Visitor visitor =
        visitorRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Visitor not found: " + id));

    visitorRepository.delete(visitor);

    updateUserRoles(visitor.getUser().getId());

    return ResponseEntity.noContent().build();
  }

  private void updateUserRoles(final UUID userId) {
    if (visitorRepository.findByUserId(userId).isEmpty()) {
      final User user =
          userRepository
              .findById(userId)
              .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
      final Role role =
          roleRepository
              .findByName("VISITOR")
              .orElseThrow(() -> new EntityNotFoundException("Role not found: " + "VISITOR"));
      final Set<Role> roles = user.getRoles();
      roles.remove(role);
      user.setRoles(roles);
      userRepository.save(user);
    }
  }
}
