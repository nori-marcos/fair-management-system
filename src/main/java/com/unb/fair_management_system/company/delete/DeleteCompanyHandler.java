package com.unb.fair_management_system.company.delete;

import com.unb.fair_management_system.company.Company;
import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteCompanyHandler implements Handler<UUID, Void> {
  private final CompanyRepository companyRepository;

  @Override
  public ResponseEntity<Void> handle(final UUID id) {
    final Company company =
        companyRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Company not found: " + id));
    companyRepository.delete(company);
    return ResponseEntity.noContent().build();
  }
}
