package com.unb.fair_management_system.product.delete;

import com.unb.fair_management_system.product.ProductRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteProductHandler implements Handler<UUID, Void> {

  private final ProductRepository productRepository;

  @Override
  public ResponseEntity<Void> handle(final UUID id) {
    if (!productRepository.existsById(id)) {
      throw new EntityNotFoundException("Product not found: " + id);
    }
    productRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
