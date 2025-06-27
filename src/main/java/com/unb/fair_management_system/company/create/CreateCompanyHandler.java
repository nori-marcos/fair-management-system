package com.unb.fair_management_system.company.create;

import com.unb.fair_management_system.company.Company;
import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateCompanyHandler implements Handler<CreateCompanyRequest, UUID> {

  private final CompanyRepository companyRepository;

  @Override
  public ResponseEntity<UUID> handle(final CreateCompanyRequest request) {
    final Company company =
        new Company(
            request.name(), request.email(), request.phone(), request.cnpj(), request.createdBy());
    return ResponseEntity.status(HttpStatus.CREATED).body(companyRepository.save(company).getId());
  }
}
