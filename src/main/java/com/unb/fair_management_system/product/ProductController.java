package com.unb.fair_management_system.product;

import com.unb.fair_management_system.starter.config.swagger.GetApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Products")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
  private final ProductRepository productRepository;

  @Operation(summary = "List")
  @GetApiResponses
  @GetMapping
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }
}
