package com.unb.fair_management_system.authentication.user;

import com.unb.fair_management_system.authentication.user.create.CreateUserRequest;
import com.unb.fair_management_system.authentication.user.update.UpdateUserRequest;
import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final Mediator mediator;

  @PostMapping
  @Operation(summary = "Create a new user")
  public ResponseEntity<User> create(@RequestBody @Valid final CreateUserRequest request) {
    return mediator.handle(request, User.class);
  }

  @PutMapping
  @Operation(summary = "Update an existing user")
  public ResponseEntity<User> update(@RequestBody @Valid final UpdateUserRequest request) {
    return mediator.handle(request, User.class);
  }

  @DeleteMapping("/{email}")
  @Operation(summary = "Delete a user by ID")
  public ResponseEntity<Void> delete(@PathVariable @Email final String email) {
    return mediator.handle(email, Void.class);
  }

  @GetMapping
  @Operation(summary = "List all users")
  public ResponseEntity<List<User>> list() {
    return mediator.handle(
        new EmptyRequest(), ResolvableType.forClassWithGenerics(List.class, User.class));
  }
}
