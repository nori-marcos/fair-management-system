package com.unb.fair_management_system.fair;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.fair.create.CreateFairRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.starter.swagger.GetApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Fairs")
@RequestMapping("/fairs")
@RequiredArgsConstructor
public class FairController {

  private final Mediator mediator;

  @Operation(summary = "Create a new fair")
  @GetApiResponses
  @PostMapping
  public ResponseEntity<UUID> create(@RequestBody final CreateFairRequest request) {
    return mediator.handle(request, UUID.class);
  }

  @Operation(summary = "List all fairs")
  @GetApiResponses
  @GetMapping
  public ResponseEntity<List<Fair>> list() {
    return mediator.handle(
        new EmptyRequest(), ResolvableType.forClassWithGenerics(List.class, Fair.class));
  }
}
