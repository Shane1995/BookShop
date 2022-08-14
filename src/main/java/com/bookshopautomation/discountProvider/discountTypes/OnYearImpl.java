package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.models.Book;
import com.bookshopautomation.models.CheckoutOrder;

import java.math.BigDecimal;
import java.time.Year;

public class OnYearImpl extends Discount {

  private Year yearClause;
  private int discountPercentage = 10;

  public OnYearImpl(Year yearClause, int discountPercentage) {
    this.yearClause = yearClause;
    this.discountPercentage = discountPercentage;
  }

  @Override
  public void setDiscountPercentage(int percentage) {
    this.discountPercentage = percentage;
  }

  @Override
  public <T> void updateDiscountClause(T value) {
    yearClause = (Year) value;
  }

  @Override
  public CheckoutOrder applyCondition(CheckoutOrder checkoutOrder) {
    BigDecimal deductibleDiscountTotal = new BigDecimal("00");

    for (Book book:checkoutOrder.getItems()) {
      if (book.getYear().isAfter(yearClause)) {
        deductibleDiscountTotal = deductibleDiscountTotal.add(book.getPrice().divide(BigDecimal.valueOf(discountPercentage)));
      }
    }

    BigDecimal updatedTotal = checkoutOrder.getTotalPrice().subtract(deductibleDiscountTotal);
    return new CheckoutOrder(checkoutOrder.getItems(), updatedTotal);
  }
}
