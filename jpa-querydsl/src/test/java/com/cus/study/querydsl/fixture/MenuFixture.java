package com.cus.study.querydsl.fixture;

import com.cus.study.querydsl.menu.domain.Menu;
import com.cus.study.querydsl.menu.domain.MenuItem;

import java.util.Arrays;

import static com.cus.study.querydsl.fixture.ProductFixture.*;

public class MenuFixture {
  public static final MenuItem 단품_피자 = MenuItem.builder().product(상품_피자).build();
  public static final MenuItem 단품_콜라 = MenuItem.builder().product(상품_콜라).build();
  public static final MenuItem 단품_피클 = MenuItem.builder().product(상품_피클).build();

  public static final Menu 피자_콜라_세트() {
    Menu menu = Menu.builder().name("피자 콜라 세트").menuItems(Arrays.asList(단품_피자, 단품_피클, 단품_콜라)).build();
    menu.getMenuItems().forEach(it -> it.changeMenu(menu));
    return menu;
  }
}
