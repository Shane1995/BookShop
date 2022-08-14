package com.bookshopautomation;

import com.bookshopautomation.discountProvider.DiscountProvider;
import com.bookshopautomation.discountProvider.discountTypes.Discount;
import com.bookshopautomation.discountProvider.exceptions.DiscountPercentageException;
import com.bookshopautomation.models.Book;
import com.bookshopautomation.models.CheckoutOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Checkout {
  private final DiscountProvider discountProvider;

  public Checkout(DiscountProvider discountProvider) {
    this.discountProvider = discountProvider;
  }

  public BigDecimal checkoutItems(List<Book> books) throws DiscountPercentageException {
    BigDecimal rawTotalPrice = getRawTotalPrice(books);
    CheckoutOrder checkoutOrder = new CheckoutOrder(books, rawTotalPrice);

    return calculateFinalTotalPrice(checkoutOrder).setScale(2, RoundingMode.DOWN);
  }

  private BigDecimal getRawTotalPrice(List<Book> books) {
    BigDecimal rawTotalPrice = BigDecimal.valueOf(00.00);

    for (Book book : books) {
      rawTotalPrice = rawTotalPrice.add(book.getPrice());
    }

    return rawTotalPrice;
  }

  private BigDecimal calculateFinalTotalPrice(CheckoutOrder checkoutOrder) throws DiscountPercentageException {
    List<Discount> discounts = discountProvider.getDiscounts();

    for (Discount discount : discounts) {
      checkoutOrder = discount.applyCondition(checkoutOrder);
    }

    return checkoutOrder.getTotalPrice();
  }
}
