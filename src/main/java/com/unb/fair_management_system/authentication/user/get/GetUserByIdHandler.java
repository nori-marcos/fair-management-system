package com.unb.fair_management_system.authentication.user.get;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetUserByIdHandler implements Handler<UUID, User> {

  private final UserRepository userRepository;

  @Override
  public ResponseEntity<User> handle(final UUID uuid) {
    final User user =
        userRepository
            .findById(uuid)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return ResponseEntity.ok(user);
  }
}
