package com.unb.fair_management_system.company.list;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.product.ProductResponse;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ListCompaniesHandler implements Handler<EmptyRequest, List<ListCompaniesResponse>> {

  private final CompanyRepository companyRepository;

  @Override
  public ResponseEntity<List<ListCompaniesResponse>> handle(final EmptyRequest request) {
    final List<ListCompaniesResponse> companies =
        companyRepository.findAll().stream()
            .map(
                company ->
                    new ListCompaniesResponse(
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
                            .toList()))
            .toList();
    return ResponseEntity.ok(companies);
  }
}
