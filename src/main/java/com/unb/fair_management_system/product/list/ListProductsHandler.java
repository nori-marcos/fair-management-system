package com.unb.fair_management_system.product.list;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.product.Product;
import com.unb.fair_management_system.product.ProductRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ListProductsHandler implements Handler<EmptyRequest, List<ProductResponse>> {
  private final ProductRepository productRepository;

  @Override
  public ResponseEntity<List<ProductResponse>> handle(final EmptyRequest request) {
    final List<Product> products = productRepository.findAll();
    final List<ProductResponse> response =
        products.stream()
            .map(
                product ->
                    new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCompany().getId()))
            .toList();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
