package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.discountProvider.exceptions.DiscountPercentageException;
import com.bookshopautomation.discountProvider.validation.ValidatePercentage;
import com.bookshopautomation.models.Book;
import com.bookshopautomation.models.CheckoutOrder;

import java.math.BigDecimal;

public class onTitleDiscountImpl extends Discount {

  private String titleClause;
  private int discountPercentage;

  public onTitleDiscountImpl(String titleClause, int discountPercentage) {
    this.titleClause = titleClause;
    this.discountPercentage = discountPercentage;
  }

  @Override
  public void setDiscountPercentage(int percentage) {
    discountPercentage = percentage;
  }

  @Override
  public <T> void updateDiscountClause(T value) {
    titleClause = (String) value;
  }

  @Override
  public CheckoutOrder applyCondition(CheckoutOrder checkoutOrder) throws DiscountPercentageException {

    ValidatePercentage.allCasesValid(discountPercentage);

    BigDecimal deductibleDiscountTotal = new BigDecimal("00");

    for (Book book: checkoutOrder.getItems()) {
      if (book.getTitle().equals(titleClause)){
        BigDecimal deductibleAmount = super.calculateDeductibleDiscount(book.getPrice(), discountPercentage);
        deductibleDiscountTotal = deductibleDiscountTotal.add(deductibleAmount);
      }
    }

    BigDecimal updatedTotal = super.calculateUpdatedTotalCost(checkoutOrder.getTotalPrice(), deductibleDiscountTotal);
    return new CheckoutOrder(checkoutOrder.getItems(), updatedTotal);
  }
}
