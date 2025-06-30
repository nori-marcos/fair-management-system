package com.unb.fair_management_system.visitor;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.starter.swagger.GetApiResponses;
import com.unb.fair_management_system.ticket.TicketResponse;
import com.unb.fair_management_system.visitor.create.CreateVisitorRequest;
import com.unb.fair_management_system.visitor.list.VisitorResponse;
import com.unb.fair_management_system.visitor.update.UpdateVisitorRequest;
import com.unb.fair_management_system.visitor.update.UpdateVisitorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Visitors")
@RestController
@RequestMapping("/visitors")
@RequiredArgsConstructor
public class VisitorController {

  private final Mediator mediator;

  @Operation(summary = "Create a new visitor and issue a ticket")
  @GetApiResponses
  @PostMapping
  public ResponseEntity<TicketResponse> create(@RequestBody @Valid final CreateVisitorRequest request) {
    return mediator.handle(request, TicketResponse.class);
  }

  @Operation(summary = "List all visitors")
  @GetApiResponses
  @GetMapping
  public ResponseEntity<List<VisitorResponse>> list() {
    return mediator.handle(
        new EmptyRequest(), ResolvableType.forClassWithGenerics(List.class, VisitorResponse.class));
  }

  @Operation(summary = "Update an existing visitor")
  @GetApiResponses
  @PutMapping
  public ResponseEntity<UpdateVisitorResponse> update(
      @RequestBody @Valid final UpdateVisitorRequest request) {
    return mediator.handle(request, UpdateVisitorResponse.class);
  }

  @Operation(summary = "Delete a visitor by ID")
  @GetApiResponses
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable final java.util.UUID id) {
    return mediator.handle(id, Void.class);
  }
}
