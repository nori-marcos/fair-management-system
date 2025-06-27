package com.unb.fair_management_system.product.update;

import com.unb.fair_management_system.product.Product;
import com.unb.fair_management_system.product.ProductRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateProductHandler implements Handler<UpdateProductRequest, UpdateProductResponse> {

  private final ProductRepository productRepository;

  @Override
  public ResponseEntity<UpdateProductResponse> handle(final UpdateProductRequest request) {
    final Product product =
        productRepository
            .findById(request.id())
            .orElseThrow(() -> new EntityNotFoundException("Product not found: " + request.id()));

    product.setName(request.name());
    product.setDescription(request.description());
    product.setPrice(request.price());
    product.setCreatedBy(request.updatedBy());

    final Product updated = productRepository.save(product);
    return ResponseEntity.ok(
        new UpdateProductResponse(
            updated.getId(),
            updated.getName(),
            updated.getDescription(),
            updated.getPrice(),
            updated.getExhibitor().getId()));
  }
}
