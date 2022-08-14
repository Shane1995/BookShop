package com.bookshopautomation.discountProvider;

import com.bookshopautomation.discountProvider.discountTypes.Discount;

import java.util.ArrayList;
import java.util.List;

public class DiscountBookProviderImpl implements DiscountProvider{

  private List<Discount> discounts;

  public DiscountBookProviderImpl() {
    this.discounts = new ArrayList<>();
  }

  @Override
  public void addDiscount(Discount discount) {
    discounts.add(discount);
  }

  @Override
  public List<Discount> getDiscounts() {
    return discounts;
  }
}
