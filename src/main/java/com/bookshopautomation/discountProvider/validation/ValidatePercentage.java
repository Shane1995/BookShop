package com.bookshopautomation.discountProvider.validation;

import com.bookshopautomation.discountProvider.exceptions.DiscountPercentageException;

public class  ValidatePercentage {

  public static void allCasesValid(int percentage) throws DiscountPercentageException {
    isLowerBoundValid(percentage);
    isHigherBoundValid(percentage);
  }

  public static void isLowerBoundValid(int percentage) throws DiscountPercentageException {
    if (percentage < 0){
      throw new DiscountPercentageException("Percentage applied needs to be higher than 0%");
    }
  }

  public static void isHigherBoundValid(int percentage) throws DiscountPercentageException {
    // 100 % off? is that a thing?
    if (percentage > 100){
      throw new DiscountPercentageException("Percentage applied needs to be lower than or equal to 100%");
    }
  }
}
