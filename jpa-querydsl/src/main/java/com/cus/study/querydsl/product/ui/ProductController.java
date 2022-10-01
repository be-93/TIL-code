package com.cus.study.querydsl.product.ui;

import com.cus.study.querydsl.product.application.ProductService;
import com.cus.study.querydsl.product.dto.ProductRequest;
import com.cus.study.querydsl.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductResponse>> findAll() {
    return ResponseEntity.ok(productService.findAll());
  }

  @PostMapping
  public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest.Create request) {
    return ResponseEntity.ok(productService.create(request));
  }

}
