package com.unb.fair_management_system.company;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.company.create.CreateCompanyRequest;
import com.unb.fair_management_system.company.list.ListCompaniesResponse;
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
  public ResponseEntity<UUID> create(@RequestBody final CreateCompanyRequest request) {
    return mediator.handle(request, UUID.class);
  }

  @GetMapping
  @Operation(summary = "List all companies")
  public ResponseEntity<List<ListCompaniesResponse>> listAll() {
    return mediator.handle(
        new EmptyRequest(),
        ResolvableType.forClassWithGenerics(List.class, ListCompaniesResponse.class));
  }
}
