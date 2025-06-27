package com.unb.fair_management_system.authentication.user.create;

import com.unb.fair_management_system.authentication.role.Role;
import com.unb.fair_management_system.authentication.role.RoleRepository;
import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateUserHandler implements Handler<CreateUserRequest, User> {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public ResponseEntity<User> handle(final CreateUserRequest request) {

    final Set<Role> roles = new HashSet<>();
    for (final String roleName : request.roles()) {
      final Role role =
          roleRepository
              .findByName(roleName)
              .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
      roles.add(role);
    }

    final User user =
        new User(
            request.fullName(), request.email(), request.password(), roles, request.createdBy());

    final User saved = userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }
}
