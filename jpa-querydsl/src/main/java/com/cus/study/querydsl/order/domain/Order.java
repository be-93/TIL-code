package com.cus.study.querydsl.order.domain;

import com.cus.study.querydsl.delivery.domain.Delivery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String memo;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  @OneToMany(cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.changeOrder(this);
  }

  public void changeMemo(String memo) {
    this.memo = memo;
  }
}
