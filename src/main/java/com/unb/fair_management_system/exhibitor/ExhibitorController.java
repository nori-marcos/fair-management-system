package com.unb.fair_management_system.exhibitor;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.exhibitor.create.CreateExhibitorRequest;
import com.unb.fair_management_system.exhibitor.list.ExhibitorResponse;
import com.unb.fair_management_system.exhibitor.update.UpdateExhibitorRequest;
import com.unb.fair_management_system.exhibitor.update.UpdateExhibitorResponse;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.ticket.TicketResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
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
  public ResponseEntity<TicketResponse> create(@RequestBody final CreateExhibitorRequest request) {
    return mediator.handle(request, TicketResponse.class);
  }

  @PutMapping
  @Operation(summary = "Update an exhibitor")
  public ResponseEntity<UpdateExhibitorResponse> update(
      @RequestBody final UpdateExhibitorRequest request) {
    return mediator.handle(request, UpdateExhibitorResponse.class);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an exhibitor by ID")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    return mediator.handle(id, Void.class);
  }

  @GetMapping
  @Operation(summary = "List all exhibitors")
  public ResponseEntity<List<ExhibitorResponse>> list() {
    return mediator.handle(
        new EmptyRequest(),
        ResolvableType.forClassWithGenerics(List.class, ExhibitorResponse.class));
  }
}
