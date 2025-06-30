package com.unb.fair_management_system.authentication.user.delete;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DeleteUserHandler implements Handler<UUID, Void> {

  private final UserRepository userRepository;

  @Override
  public ResponseEntity<Void> handle(final UUID id) {
    final User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    userRepository.delete(user);
    return ResponseEntity.noContent().build();
  }
}
