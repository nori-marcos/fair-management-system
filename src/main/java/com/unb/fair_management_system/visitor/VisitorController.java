package com.unb.fair_management_system.visitor;

import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.starter.swagger.GetApiResponses;
import com.unb.fair_management_system.ticket.Ticket;
import com.unb.fair_management_system.visitor.create.CreateVisitorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<Ticket> create(@RequestBody @Valid final CreateVisitorRequest request) {
    return mediator.handle(request, Ticket.class);
  }
}
