package com.cus.study.querydsl.example;

import com.cus.study.querydsl.configuration.TestJPAQueryFactory;
import com.cus.study.querydsl.domain.order.Order;
import com.cus.study.querydsl.domain.order.QOrder;
import com.cus.study.querydsl.domain.product.Product;
import com.cus.study.querydsl.domain.product.ProductRepository;
import com.cus.study.querydsl.domain.product.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestJPAQueryFactory.class)
public class SimpleTest {

  @Autowired
  private JPAQueryFactory factory;
  @Autowired
  private ProductRepository productRepository;

  @Test
  public void select() {
    //given
    IntStream.rangeClosed(1, 10)
        .forEach(index -> productRepository.save(Product.builder().name(index + "번째 상품")
            .price(BigDecimal.TEN
                .multiply(BigDecimal.valueOf(index)))
            .build())
        );

    //when
    List<Product> products = factory.selectFrom(QProduct.product).fetch();

    //then
    assertThat(products).isNotEmpty();
  }
}
