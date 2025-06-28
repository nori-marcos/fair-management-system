package com.unb.fair_management_system.company.update;

import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateCompanyHandler implements Handler<UpdateCompanyRequest, UpdateCompanyResponse> {

  private final CompanyRepository companyRepository;

  @Override
  public ResponseEntity<UpdateCompanyResponse> handle(final UpdateCompanyRequest request) {
    final var company =
        companyRepository
            .findById(request.id())
            .orElseThrow(() -> new EntityNotFoundException("Company not found: " + request.id()));

    company.setName(request.name());
    company.setEmail(request.email());
    company.setPhone(request.phone());
    company.setCnpj(request.cnpj());

    final var updatedCompany = companyRepository.save(company);
    return ResponseEntity.ok(
        new UpdateCompanyResponse(
            updatedCompany.getName(),
            updatedCompany.getEmail(),
            updatedCompany.getPhone(),
            updatedCompany.getCnpj()));
  }
}
