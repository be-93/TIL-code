package com.cus.study.querydsl.product.dto;

import com.cus.study.querydsl.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class ProductRequest {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Create {
    private String name;
    private BigDecimal price;

    public Product toProduct() {
      return new Product(name, price);
    }
  }

}
