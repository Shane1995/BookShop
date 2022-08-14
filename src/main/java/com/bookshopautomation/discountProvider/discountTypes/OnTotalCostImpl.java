package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.models.CheckoutOrder;
import java.math.BigDecimal;

public class OnTotalCostImpl implements Discount {

  private BigDecimal totalCostClause;
  private int discountPercentage = 5;

  public OnTotalCostImpl(BigDecimal totalCostClause, int discountPercentage) {
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
  public CheckoutOrder applyCondition(CheckoutOrder checkoutOrder) {

    if (checkoutOrder.getTotalPrice().longValue() < totalCostClause.longValue()){
      return checkoutOrder;
    }

    float percentage = discountPercentage/100f;

    BigDecimal deductibleDiscount = checkoutOrder.getTotalPrice().multiply(BigDecimal.valueOf(percentage));
    BigDecimal updatedTotal = checkoutOrder.getTotalPrice().subtract(deductibleDiscount);

    return new CheckoutOrder(checkoutOrder.getItems(), updatedTotal);
  }
}
