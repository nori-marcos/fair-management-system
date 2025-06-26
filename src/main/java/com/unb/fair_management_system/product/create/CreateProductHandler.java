package com.unb.fair_management_system.product.create;

import com.unb.fair_management_system.product.Product;
import com.unb.fair_management_system.product.ProductRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateProductHandler implements Handler<CreateProductRequest, UUID> {
  private final ProductRepository productRepository;

  @Override
  public ResponseEntity<UUID> handle(final CreateProductRequest request) {
    final Product product =
        new Product(request.name(), request.description(), request.price(), request.createdBy());
    final Product saved = productRepository.save(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved.getId());
  }
}
