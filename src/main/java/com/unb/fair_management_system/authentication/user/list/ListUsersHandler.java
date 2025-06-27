package com.unb.fair_management_system.authentication.user.list;

import com.unb.fair_management_system.authentication.user.User;
import com.unb.fair_management_system.authentication.user.UserRepository;
import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListUsersHandler implements Handler<EmptyRequest, List<User>> {

  private final UserRepository userRepository;

  @Override
  public ResponseEntity<List<User>> handle(final EmptyRequest request) {
    final List<User> users = userRepository.findAll();
    return ResponseEntity.ok(users);
  }
}
