package com.bookshopautomation;

import com.bookshopautomation.discountProvider.DiscountProvider;
import com.bookshopautomation.discountProvider.discountTypes.Discount;
import com.bookshopautomation.models.Book;
import com.bookshopautomation.models.CheckoutOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Checkout {
  private final DiscountProvider discountProvider;

  public Checkout(DiscountProvider discountProvider) {
    this.discountProvider = discountProvider;
  }

  public BigDecimal checkoutItems(List<Book> books) {
    return null;
  }
}
