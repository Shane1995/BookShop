package com.bookshopautomation.discountProvider;

import com.bookshopautomation.discountProvider.discountTypes.Discount;

import java.util.List;

public interface DiscountProvider {
  void addDiscount(Discount discount);
  List<Discount> getDiscounts();
}
