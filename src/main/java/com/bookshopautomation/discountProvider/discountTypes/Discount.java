package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.discountProvider.exceptions.DiscountPercentageException;
import com.bookshopautomation.models.CheckoutOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Discount {

  abstract public void setDiscountPercentage(int percentage);
  abstract public <T> void updateDiscountClause(T value);
  abstract public CheckoutOrder applyCondition(CheckoutOrder checkoutOrder) throws DiscountPercentageException;

  public BigDecimal calculateDeductibleDiscount(BigDecimal price, int discountPercentage){
    float percentage = discountPercentage/100f;
    return price.multiply(BigDecimal.valueOf(percentage));
  }

  public BigDecimal calculateUpdatedTotalCost(BigDecimal oldTotalPrice, BigDecimal totalDeductibleDiscount){
    return oldTotalPrice.subtract(totalDeductibleDiscount);
  }
}
