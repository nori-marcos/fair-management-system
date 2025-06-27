package com.unb.fair_management_system.authentication.user.update;

import com.unb.fair_management_system.authentication.role.Role;
import com.unb.fair_management_system.authentication.role.RoleRepository;
import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateUserHandler implements Handler<UpdateUserRequest, User> {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public ResponseEntity<User> handle(final UpdateUserRequest request) {
    final User user =
        userRepository
            .findByEmail(request.email())
            .orElseThrow(
                () -> new EntityNotFoundException("User not found with email: " + request.email()));

    final Set<Role> roles = new HashSet<>();
    for (final String roleName : request.roles()) {
      final Role role =
          roleRepository
              .findByName(roleName)
              .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
      roles.add(role);
    }

    user.setFullName(request.fullName());
    user.setPassword(request.password());
    user.setRoles(roles);
    user.setCreatedBy(request.updatedBy());

    final User updated = userRepository.save(user);
    return ResponseEntity.ok(updated);
  }
}
