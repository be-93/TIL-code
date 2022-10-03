package com.cus.study.querydsl.fixture;

import com.cus.study.querydsl.order.domain.Order;
import com.cus.study.querydsl.order.domain.OrderItem;

import static com.cus.study.querydsl.fixture.MenuFixture.피자_콜라_세트;

public class OrderFixture {
  public static Order 피자_콜라_세트_주문_생성() {
    OrderItem orderItem = new OrderItem();
    orderItem.changeMenu(피자_콜라_세트());

    Order order = new Order();
    order.addOrderItem(orderItem);

    return order;
  }
}
