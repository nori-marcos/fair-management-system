package com.unb.fair_management_system.authentication.user.delete;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteUserHandler implements Handler<String, Void> {

  private final UserRepository userRepository;

  @Override
  public ResponseEntity<Void> handle(final String email) {
    final User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
    userRepository.delete(user);
    return ResponseEntity.noContent().build();
  }
}
