package com.unb.fair_management_system.company.list;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.company.Company;
import com.unb.fair_management_system.company.CompanyRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ListCompaniesHandler implements Handler<EmptyRequest, List<Company>> {

  private final CompanyRepository companyRepository;

  @Override
  public ResponseEntity<List<Company>> handle(final EmptyRequest request) {
    return ResponseEntity.ok(companyRepository.findAll());
  }
}
