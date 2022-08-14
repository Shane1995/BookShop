package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.models.CheckoutOrder;

public abstract class Discount {

  abstract public void setDiscountPercentage(int percentage);

  abstract public <T> void updateDiscountClause(T value);
  abstract public CheckoutOrder applyCondition(CheckoutOrder checkoutOrder);
}
