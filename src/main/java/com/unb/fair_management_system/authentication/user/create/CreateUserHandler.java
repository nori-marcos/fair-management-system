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
  private final PasswordEncoder passwordEncoder; // Inject the password encoder

  @Override
  public ResponseEntity<UUID> handle(final CreateUserRequest request) {
    if (userRepository.findByEmail(request.email()).isPresent()) {
      // In a real web flow, this should be handled gracefully.
      throw new IllegalStateException("Email already exists: " + request.email());
    }

    final Set<Role> roles = new HashSet<>();
    for (final String roleName : request.roles()) {
      final Role role = roleRepository.findByName(roleName)
                            .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
      roles.add(role);
    }

    // IMPORTANT: Always encode the password before saving
    final String encodedPassword = passwordEncoder.encode(request.password());

    final User user = new User(
        request.fullName(),
        request.email(),
        encodedPassword, // Save the encoded password
        roles,
        request.createdBy() != null ? request.createdBy() : "self-registration"
    );

    final UUID savedId = userRepository.save(user).getId();
    return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
  }
}
