package com.cus.study.querydsl.product.application;

import com.cus.study.querydsl.product.domain.ProductRepository;
import com.cus.study.querydsl.product.domain.QProduct;
import com.cus.study.querydsl.product.dto.ProductRequest;
import com.cus.study.querydsl.product.dto.ProductResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final JPAQueryFactory factory;

  public ProductResponse create(ProductRequest.Create request) {
    return ProductResponse.of(productRepository.save(request.toProduct()));
  }

  public List<ProductResponse> findAll() {
    return factory.selectFrom(QProduct.product)
        .fetch()
        .stream()
        .map(ProductResponse::of)
        .collect(Collectors.toList());
  }
}
