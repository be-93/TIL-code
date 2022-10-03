package com.cus.study.querydsl.order.domain;

import com.cus.study.querydsl.menu.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "menu_id")
  private Menu menu;

  public void changeMenu(Menu menu) {
    this.menu = menu;
  }

  public void changeOrder(Order order) {
    this.order = order;
  }

}
