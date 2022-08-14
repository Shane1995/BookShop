package com.bookshopautomation;

import com.bookshopautomation.discountProvider.DiscountBookProviderImpl;
import com.bookshopautomation.discountProvider.DiscountProvider;
import com.bookshopautomation.discountProvider.discountTypes.Discount;
import com.bookshopautomation.discountProvider.discountTypes.OnTotalCostImpl;
import com.bookshopautomation.discountProvider.discountTypes.OnYearImpl;
import com.bookshopautomation.models.Book;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutTests {

  public DiscountProvider discountProvider;

  @Before
  public void Setup(){
    discountProvider = new DiscountBookProviderImpl();
  }

  @Test
  public void checkoutItems_GivenBookAfter2000_ShouldApplyOnYearDiscount() throws Exception {

    // Arrange
    BigDecimal expectedPrice = BigDecimal.valueOf(24.69).setScale(2);
    List<Book> book = new ArrayList<Book>();
    book.add(new Book("The Terrible Privacy of Maxwell Sim", Year.parse("2010"), BigDecimal.valueOf(13.14)));
    book.add(new Book("Three Men in a Boat", Year.parse("1889"), BigDecimal.valueOf(12.87)));

    Discount onYearDiscount = new OnYearImpl();
    onYearDiscount.setDiscountPercentage(10);

    discountProvider.addDiscount(onYearDiscount);

    Checkout checkout = new Checkout(discountProvider);

    // Act
    BigDecimal finalTotalCost = checkout.checkoutItems(book);

    // Assert
    assertEquals(expectedPrice, finalTotalCost);
  }

  @Test
  public void checkoutItems_GivenBooksOver30Pounds_ShouldApplyOnTotalDiscountCost() {

    // Arrange
    BigDecimal expectedPrice = BigDecimal.valueOf(35.27).setScale(2);
    List<Book> book = new ArrayList<Book>();
    book.add(new Book("Still Life With Woodpecker", Year.parse("1980"), BigDecimal.valueOf(11.05)));
    book.add(new Book("Three Men in a Boat", Year.parse("1889"), BigDecimal.valueOf(12.87)));
    book.add(new Book("Great Expectations", Year.parse("1861"), BigDecimal.valueOf(13.21)));

    Discount onTotalDiscount = new OnTotalCostImpl();
    onTotalDiscount.setDiscountPercentage(5);
    onTotalDiscount.updateDiscountClause(BigDecimal.valueOf(30));

    discountProvider.addDiscount(onTotalDiscount);

    Checkout checkout = new Checkout(discountProvider);

    // Act
    BigDecimal finalTotalCost = checkout.checkoutItems(book);

    // Assert
    assertEquals(expectedPrice, finalTotalCost);
  }

  @Test
  public void checkoutItems_GivenMixtureOfBooks_ShouldApplyCorrectDiscountCost() {

    // Arrange
    BigDecimal expectedPrice = BigDecimal.valueOf(36.01).setScale(2);
    List<Book> book = new ArrayList<Book>();
    book.add(new Book("The Terrible Privacy of Maxwell Sim", Year.parse("2010"), BigDecimal.valueOf(13.14)));
    book.add(new Book("Three Men in a Boat", Year.parse("1889"), BigDecimal.valueOf(12.87)));
    book.add(new Book("Great Expectations", Year.parse("1861"), BigDecimal.valueOf(13.21)));

    Discount onYearDiscount = new OnYearImpl();
    onYearDiscount.updateDiscountClause(Year.parse("2000"));
    onYearDiscount.setDiscountPercentage(10);

    Discount onTotalDiscount = new OnTotalCostImpl();
    onTotalDiscount.setDiscountPercentage(5);
    onTotalDiscount.updateDiscountClause(BigDecimal.valueOf(30));

    discountProvider.addDiscount(onYearDiscount);
    discountProvider.addDiscount(onTotalDiscount);

    Checkout checkout = new Checkout(discountProvider);

    // Act
    BigDecimal finalTotalCost = checkout.checkoutItems(book);

    // Assert
    assertEquals(expectedPrice, finalTotalCost);
  }

  @Test
  public void checkoutItems_GivenSingleBookAfter2000_ShouldApplyOnYearDiscount() {

    // Arrange
    BigDecimal expectedPrice = BigDecimal.valueOf(22.22).setScale(2);
    List<Book> book = new ArrayList<Book>();
    book.add(new Book("The Terrible Privacy of Maxwell Sim", Year.parse("2010"), BigDecimal.valueOf(24.69)));

    Discount onYearDiscount = new OnYearImpl();
    onYearDiscount.updateDiscountClause(Year.parse("2000"));
    onYearDiscount.setDiscountPercentage(10);

    discountProvider.addDiscount(onYearDiscount);

    Checkout checkout = new Checkout(discountProvider);

    // Act
    BigDecimal finalTotalCost = checkout.checkoutItems(book);

    // Assert
    assertEquals(expectedPrice, finalTotalCost);
  }

  @Test
  public void checkoutItems_GivenMoreThanOneBookAfter2000_ShouldApplyOnYearDiscount() {

    // Arrange
    BigDecimal expectedPrice = BigDecimal.valueOf(67.22).setScale(2);
    List<Book> book = new ArrayList<Book>();
    book.add(new Book("The Terrible Privacy of Maxwell Sim", Year.parse("2010"), BigDecimal.valueOf(24.69)));
    book.add(new Book("Harry Squatter", Year.parse("2011"), BigDecimal.valueOf(50.00)));

    Discount onYearDiscount = new OnYearImpl();
    onYearDiscount.updateDiscountClause(Year.parse("2000"));
    onYearDiscount.setDiscountPercentage(10);

    discountProvider.addDiscount(onYearDiscount);

    Checkout checkout = new Checkout(discountProvider);

    // Act
    BigDecimal finalTotalCost = checkout.checkoutItems(book);

    // Assert
    assertEquals(expectedPrice, finalTotalCost);
  }
}
