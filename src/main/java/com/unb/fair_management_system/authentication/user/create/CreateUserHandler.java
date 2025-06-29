
package com.unb.fair_management_system.authentication.user.create;

import com.unb.fair_management_system.authentication.role.Role;
import com.unb.fair_management_system.authentication.role.RoleRepository;
import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateUserHandler implements Handler<CreateUserRequest, UUID> {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public ResponseEntity<UUID> handle(final CreateUserRequest request) {
    final Optional<User> existingUserOptional = userRepository.findByEmail(request.email());

    final Set<Role> newRoles = new HashSet<>();
    if (!request.roles().isEmpty()) {
      for (final String roleName : request.roles()) {
        final Role role =
            roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
        newRoles.add(role);
      }
    }

    if (existingUserOptional.isPresent()) {
      final User existingUser = existingUserOptional.get();
      final Set<Role> existingRoles = existingUser.getRoles();

      for (final Role newRole : newRoles) {
        if (existingRoles.stream()
            .anyMatch(existingRole -> existingRole.getId().equals(newRole.getId()))) {
          throw new IllegalStateException("The user has already been registered.");
        }
      }

      existingRoles.addAll(newRoles);
      existingUser.setRoles(existingRoles);
      final User updatedUser = userRepository.save(existingUser);
      return ResponseEntity.ok(updatedUser.getId());

    } else {
      final String encodedPassword = passwordEncoder.encode(request.password());

      final User newUser =
          new User(
              request.fullName(), request.email(), encodedPassword, newRoles, request.createdBy());

      final UUID savedId = userRepository.save(newUser).getId();
      return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
    }
  }
}
