package com.bookshopautomation.discountProvider.discountTypes;

import com.bookshopautomation.models.CheckoutOrder;

public interface Discount {

  void setDiscountPercentage(int percentage);

  <T> void updateDiscountClause(T value);
  CheckoutOrder applyCondition(CheckoutOrder checkoutOrder);
}
