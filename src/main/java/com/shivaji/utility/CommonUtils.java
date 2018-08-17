package com.shivaji.utility;

import static java.util.Arrays.stream;

import java.util.Optional;
import java.util.function.Function;

/** @author Shivaji Byrapaneni */
public class CommonUtils {

  private CommonUtils() {
  }

  public static final Function<String, Boolean> isNotEmpty = str -> str != null && !str.isEmpty();
  public static final Function<String, Boolean> isEmpty = str -> str == null || str.isEmpty();

  public static String join(String... strings) {
    Optional<String> s = stream(strings).reduce(String::concat);
    return s.orElse("");
  }
}
