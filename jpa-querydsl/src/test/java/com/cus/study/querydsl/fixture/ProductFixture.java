package com.cus.study.querydsl.fixture;

import com.cus.study.querydsl.product.domain.Product;

import java.math.BigDecimal;

public class ProductFixture {
  public static final Product 상품_피자 = Product.builder().name("피자").price(BigDecimal.valueOf(25000)).build();
  public static final Product 상품_콜라 = Product.builder().name("콜라").price(BigDecimal.valueOf(1500)).build();
  public static final Product 상품_피클 = Product.builder().name("피클").price(BigDecimal.valueOf(1000)).build();

}
