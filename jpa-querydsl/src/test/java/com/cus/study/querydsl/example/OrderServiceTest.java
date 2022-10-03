package com.cus.study.querydsl.example;

import com.cus.study.querydsl.configuration.TestJPAQueryFactory;
import com.cus.study.querydsl.order.domain.Order;
import com.cus.study.querydsl.order.domain.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.cus.study.querydsl.fixture.OrderFixture.피자_콜라_세트_주문_생성;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestJPAQueryFactory.class)
public class OrderServiceTest {
  @Autowired
  private OrderRepository orderRepository;

  @BeforeEach
  void menuSetUp() {

  }

  @Test
  @Rollback(value = false)
  public void 오더_생성() {
    List<Order> orders = new ArrayList<>();
    IntStream.rangeClosed(1, 1)
        .forEach(it -> {
          Order order = 피자_콜라_세트_주문_생성();
          order.changeMemo(it + "번째 주문");

          orders.add(order);
        });

    orderRepository.saveAll(orders);
  }
}
