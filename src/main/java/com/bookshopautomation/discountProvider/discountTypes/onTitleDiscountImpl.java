package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.discountProvider.exceptions.DiscountPercentageException;
import com.bookshopautomation.discountProvider.validation.ValidatePercentage;
import com.bookshopautomation.models.Book;
import com.bookshopautomation.models.CheckoutOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class onTitleDiscountImpl implements Discount {

  private String titleClause;
  private int discountPercentage = 33;

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
        deductibleDiscountTotal = deductibleDiscountTotal.add(book.getPrice().divide(BigDecimal.valueOf(discountPercentage),3, RoundingMode.HALF_DOWN));
      }
    }

    BigDecimal updatedTotal = checkoutOrder.getTotalPrice().subtract(deductibleDiscountTotal);
    return new CheckoutOrder(checkoutOrder.getItems(), updatedTotal);
  }
}
