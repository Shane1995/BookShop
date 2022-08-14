package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.discountProvider.exceptions.DiscountPercentageException;
import com.bookshopautomation.discountProvider.validation.ValidatePercentage;
import com.bookshopautomation.models.Book;
import com.bookshopautomation.models.CheckoutOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;

public class OnYearDiscountImpl implements Discount {

  private Year yearClause;
  private int discountPercentage = 10;

  public OnYearDiscountImpl(Year yearClause, int discountPercentage) {
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
  public CheckoutOrder applyCondition(CheckoutOrder checkoutOrder) throws DiscountPercentageException {
    ValidatePercentage.allCasesValid(discountPercentage);

    BigDecimal deductibleDiscountTotal = new BigDecimal("00");

    for (Book book:checkoutOrder.getItems()) {
      if (book.getYear().isAfter(yearClause)) {
        deductibleDiscountTotal = deductibleDiscountTotal.add(book.getPrice().divide(BigDecimal.valueOf(discountPercentage),3, RoundingMode.HALF_DOWN));
      }
    }

    BigDecimal updatedTotal = checkoutOrder.getTotalPrice().subtract(deductibleDiscountTotal);
    return new CheckoutOrder(checkoutOrder.getItems(), updatedTotal);
  }
}
