package com.unb.fair_management_system.authentication.user.get;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetUserByEmailHandler implements Handler<String, User> {

  private final UserRepository userRepository;

  @Override
  public ResponseEntity<User> handle(final String email) {
    final User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return ResponseEntity.ok(user);
  }
}
