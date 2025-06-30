package com.unb.fair_management_system.company;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.company.create.CreateCompanyRequest;
import com.unb.fair_management_system.company.list.CompanyResponse;
import com.unb.fair_management_system.company.update.UpdateCompanyRequest;
import com.unb.fair_management_system.company.update.UpdateCompanyResponse;
import com.unb.fair_management_system.starter.mediator.Mediator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Companies")
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

  private final Mediator mediator;

  @PostMapping
  @Operation(summary = "Create a new company")
  public ResponseEntity<java.util.UUID> create(@RequestBody final CreateCompanyRequest request) {
    return mediator.handle(request, java.util.UUID.class);
  }

  @GetMapping
  @Operation(summary = "List all companies")
  public ResponseEntity<List<CompanyResponse>> listAll() {
    return mediator.handle(
        new EmptyRequest(),
        ResolvableType.forClassWithGenerics(List.class, CompanyResponse.class));
  }

  @PutMapping
  @Operation(summary = "Update an existing company")
  public ResponseEntity<UpdateCompanyResponse> update(
      @RequestBody final UpdateCompanyRequest request) {
    return mediator.handle(request, UpdateCompanyResponse.class);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a company")
  public ResponseEntity<Void> delete(@PathVariable final java.util.UUID id) {
    return mediator.handle(id, Void.class);
  }
}
