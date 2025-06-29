
package com.unb.fair_management_system.authentication.user.create;

import com.unb.fair_management_system.authentication.role.Role;
import com.unb.fair_management_system.authentication.role.RoleRepository;
import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
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
    final User existentUser = userRepository.findByEmail(request.email()).orElse(null);

    final Set<Role> roles = new HashSet<>();
    if (!request.roles().isEmpty()) {
      for (final String roleName : request.roles()) {
        final Role role =
            roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
        if (existentUser != null) {
          if (existentUser.getRoles().contains(role)) {
            throw new IllegalStateException("User already has role: " + roleName);
          } else {
            existentUser.getRoles().add(role);
            userRepository.save(existentUser);
            return ResponseEntity.ok(existentUser.getId());
          }
        }
        roles.add(role);
      }
    }

    final String encodedPassword = passwordEncoder.encode(request.password());

    final User user =
        new User(request.fullName(), request.email(), encodedPassword, roles, request.createdBy());

    final UUID savedId = userRepository.save(user).getId();
    return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
  }
}
