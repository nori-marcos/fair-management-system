package com.unb.fair_management_system.product;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.product.create.CreateProductRequest;
import com.unb.fair_management_system.starter.mediator.Mediator;
import com.unb.fair_management_system.starter.swagger.GetApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Products")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
  private final Mediator mediator;

  @Operation(summary = "List all products")
  @GetApiResponses
  @GetMapping
  public ResponseEntity<List<Product>> listProducts() {
    return mediator.handle(
        new EmptyRequest(), ResolvableType.forClassWithGenerics(List.class, Product.class));
  }

  @Operation(summary = "Create a new product")
  @GetApiResponses
  @PostMapping
  public ResponseEntity<UUID> createProduct(@RequestBody final CreateProductRequest request) {
    return mediator.handle(request, UUID.class);
  }

}
