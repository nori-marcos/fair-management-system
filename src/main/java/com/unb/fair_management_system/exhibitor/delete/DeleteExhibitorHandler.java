package com.unb.fair_management_system.exhibitor.delete;

import com.unb.fair_management_system.authentication.role.Role;
import com.unb.fair_management_system.authentication.role.RoleRepository;
import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteExhibitorHandler implements Handler<UUID, Void> {

  private final ExhibitorRepository exhibitorRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public ResponseEntity<Void> handle(final UUID id) {
    final Exhibitor exhibitor =
        exhibitorRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Exhibitor not found: " + id));

    exhibitorRepository.deleteById(id);

    updateUserRoles(exhibitor.getUser().getId());

    return ResponseEntity.noContent().build();
  }

  private void updateUserRoles(final UUID userId) {
    if (exhibitorRepository.findByUserId(userId).isEmpty()) {
      final User user =
          userRepository
              .findById(userId)
              .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
      final Role role =
          roleRepository
              .findByName("EXHIBITOR")
              .orElseThrow(() -> new EntityNotFoundException("Role not found: " + "VISITOR"));
      final Set<Role> roles = user.getRoles();
      roles.remove(role);
      user.setRoles(roles);
      userRepository.save(user);
    }
  }
}
