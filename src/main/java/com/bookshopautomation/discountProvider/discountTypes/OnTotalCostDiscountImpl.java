package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.discountProvider.exceptions.DiscountPercentageException;
import com.bookshopautomation.discountProvider.validation.ValidatePercentage;
import com.bookshopautomation.models.CheckoutOrder;
import java.math.BigDecimal;

public class OnTotalCostDiscountImpl extends Discount {

  private BigDecimal totalCostClause;
  private int discountPercentage;

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

    BigDecimal deductibleDiscount = super.calculateDeductibleDiscount(checkoutOrder.getTotalPrice(), discountPercentage);
    BigDecimal updatedTotal = super.calculateUpdatedTotalCost(checkoutOrder.getTotalPrice(), deductibleDiscount);

    return new CheckoutOrder(checkoutOrder.getItems(), updatedTotal);
  }
}
