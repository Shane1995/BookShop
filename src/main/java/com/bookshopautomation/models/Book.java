package com.bookshopautomation.models;

import java.math.BigDecimal;
import java.time.Year;

public class Book {
  private final String title;
  private final Year year;
  private final BigDecimal price;

  public Book(String title, Year year, BigDecimal price) {
    this.title = title;
    this.year = year;
    this.price = price;
  }

  public String getTitle() {
    return title;
  }

  public Year getYear() {
    return year;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
