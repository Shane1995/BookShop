package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.discountProvider.exceptions.DiscountPercentageException;
import com.bookshopautomation.discountProvider.validation.ValidatePercentage;
import com.bookshopautomation.models.Book;
import com.bookshopautomation.models.CheckoutOrder;

import java.math.BigDecimal;
import java.time.Year;

public class OnYearDiscountImpl extends Discount {

  private Year yearClause;
  private int discountPercentage ;

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
        BigDecimal deductibleAmount = super.calculateDeductibleDiscount(book.getPrice(), discountPercentage);
        deductibleDiscountTotal = deductibleDiscountTotal.add(deductibleAmount);
      }
    }

    BigDecimal updatedTotal = super.calculateUpdatedTotalCost(checkoutOrder.getTotalPrice(), deductibleDiscountTotal);
    return new CheckoutOrder(checkoutOrder.getItems(), updatedTotal);
  }
}
