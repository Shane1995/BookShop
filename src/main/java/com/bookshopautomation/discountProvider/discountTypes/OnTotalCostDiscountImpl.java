package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.discountProvider.exceptions.DiscountPercentageException;
import com.bookshopautomation.discountProvider.validation.ValidatePercentage;
import com.bookshopautomation.models.CheckoutOrder;
import java.math.BigDecimal;

public class OnTotalCostDiscountImpl implements Discount {

  private BigDecimal totalCostClause;
  private int discountPercentage = 5;

  public OnTotalCostDiscountImpl(BigDecimal totalCostClause, int discountPercentage) {
    this.totalCostClause = totalCostClause;
    this.discountPercentage = discountPercentage;
  }

  @Override
  public void setDiscountPercentage(int percentage) {
    this.discountPercentage = percentage;
  }

  @Override
  public <T> void updateDiscountClause(T value) {
      this.totalCostClause = (BigDecimal) value;
  }

  @Override
  public CheckoutOrder applyCondition(CheckoutOrder checkoutOrder) throws DiscountPercentageException {

    ValidatePercentage.allCasesValid(discountPercentage);

    if (checkoutOrder.getTotalPrice().longValue() < totalCostClause.longValue()){
      return checkoutOrder;
    }

    float percentage = discountPercentage/100f;

    BigDecimal deductibleDiscount = checkoutOrder.getTotalPrice().multiply(BigDecimal.valueOf(percentage));
    BigDecimal updatedTotal = checkoutOrder.getTotalPrice().subtract(deductibleDiscount);

    return new CheckoutOrder(checkoutOrder.getItems(), updatedTotal);
  }
}