package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.models.CheckoutOrder;
import java.math.BigDecimal;

public class OnTotalCostImpl extends Discount {

  private BigDecimal totalCostClause = BigDecimal.valueOf(30);
  private int discountPercentage = 5;

  @Override
  public void setDiscountPercentage(int percentage) {
    this.discountPercentage = percentage;
  }

  @Override
  public <T> void updateDiscountClause(T value) {
      this.totalCostClause = (BigDecimal) value;
  }

  @Override
  public CheckoutOrder applyCondition(CheckoutOrder checkoutOrder) {

    float percentage = discountPercentage/100f;

    BigDecimal deductibleDiscount = checkoutOrder.getTotalPrice().multiply(BigDecimal.valueOf(percentage));
    BigDecimal updatedTotal = checkoutOrder.getTotalPrice().subtract(deductibleDiscount);

    return new CheckoutOrder(checkoutOrder.getItems(), updatedTotal);
  }
}
