package com.bookshopautomation.models;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutOrder {
  private final List<Book> items;
  private final BigDecimal totalPrice;

  public CheckoutOrder(List<Book> items, BigDecimal totalPrice) {
    this.items = items;
    this.totalPrice = totalPrice;
  }

  public List<Book> getItems() {
    return items;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }
}
