package com.shivaji.utility;

import java.util.Arrays;
import java.util.function.Function;

/** @author Shivaji Byrapaneni */
public class CommonUtils {
  private CommonUtils(){}

  public static final Function<String, Boolean> isNotEmpty = str -> str != null && !str.isEmpty();
  public static final Function<String, Boolean> isEmpty = str -> str == null || str.isEmpty();

  public static String join(String... strs) {
    return Arrays.stream(strs).reduce(String::concat).get();
  }
}
