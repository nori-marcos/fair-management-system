package com.unb.fair_management_system.company.get;

import com.unb.fair_management_system.company.Company;
import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.company.list.CompanyResponse;
import com.unb.fair_management_system.product.ProductResponse;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetCompanyHandler implements Handler<UUID, CompanyResponse> {

  private final CompanyRepository companyRepository;

  @Override
  public ResponseEntity<CompanyResponse> handle(final UUID uuid) {
    final Company company =
        companyRepository
            .findById(uuid)
            .orElseThrow(() -> new IllegalArgumentException("Company not found"));
    final CompanyResponse companyResponse =
        new CompanyResponse(
            company.getId(),
            company.getName(),
            company.getEmail(),
            company.getPhone(),
            company.getCnpj(),
            company.getFairs(),
            company.getProducts().stream()
                .map(
                    product ->
                        new ProductResponse(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice()))
                .toList());
    return ResponseEntity.ok(companyResponse);
  }
}
