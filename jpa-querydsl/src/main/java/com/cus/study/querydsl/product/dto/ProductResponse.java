package com.cus.study.querydsl.product.dto;

import com.cus.study.querydsl.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
  private Long id;
  private String name;
  private BigDecimal price;

  public static ProductResponse of(Product product) {
    return new ProductResponse(product.getId(), product.getName(), product.getPrice());
  }
}
