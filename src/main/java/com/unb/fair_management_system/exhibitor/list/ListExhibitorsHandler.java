package com.unb.fair_management_system.exhibitor.list;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.product.list.ProductResponse;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ListExhibitorsHandler implements Handler<EmptyRequest, List<ExhibitorResponse>> {

  private final ExhibitorRepository exhibitorRepository;

  @Override
  public ResponseEntity<List<ExhibitorResponse>> handle(final EmptyRequest request) {
    final List<Exhibitor> exhibitors = exhibitorRepository.findAll();
    final List<ExhibitorResponse> response =
        exhibitors.stream()
            .map(
                exhibitor ->
                    new ExhibitorResponse(
                        exhibitor.getId(),
                        exhibitor.getContactName(),
                        exhibitor.getContactEmail(),
                        exhibitor.getCompany().getName(),
                        exhibitor.getCompany().getProducts().stream()
                            .map(
                                product ->
                                    new ProductResponse(
                                        product.getId(),
                                        product.getName(),
                                        product.getDescription(),
                                        product.getPrice(),
                                        exhibitor.getId()))
                            .toList()))
            .toList();
    return ResponseEntity.ok(response);
  }
}
