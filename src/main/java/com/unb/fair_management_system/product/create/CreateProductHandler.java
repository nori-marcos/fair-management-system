package com.unb.fair_management_system.product.create;

import com.unb.fair_management_system.exhibitor.Exhibitor;
import com.unb.fair_management_system.exhibitor.ExhibitorRepository;
import com.unb.fair_management_system.product.Product;
import com.unb.fair_management_system.product.ProductRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateProductHandler implements Handler<CreateProductRequest, UUID> {
  private final ProductRepository productRepository;
  private final ExhibitorRepository exhibitorRepository;

  @Override
  public ResponseEntity<UUID> handle(final CreateProductRequest request) {
    final Exhibitor exhibitor =
        exhibitorRepository
            .findById(request.exhibitorId())
            .orElseThrow(() -> new EntityNotFoundException("Exhibitor not found"));

    final Product product =
        new Product(
            request.name(), request.description(), request.price(), exhibitor, request.createdBy());

    final UUID savedId = productRepository.save(product).getId();
    return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
  }
}
