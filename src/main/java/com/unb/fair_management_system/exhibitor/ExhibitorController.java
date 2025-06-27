package com.unb.fair_management_system.exhibitor;

import com.unb.fair_management_system.exhibitor.create.CreateExhibitorRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.ticket.Ticket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Exhibitors")
@RestController
@RequestMapping("/exhibitors")
@RequiredArgsConstructor
public class ExhibitorController {

  private final Mediator mediator;

  @Operation(summary = "Create a new exhibitor and issue a ticket")
  @PostMapping
  public ResponseEntity<Ticket> create(@RequestBody final CreateExhibitorRequest request) {
    return mediator.handle(request, Ticket.class);
  }
}
