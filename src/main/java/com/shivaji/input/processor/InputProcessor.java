package com.shivaji.input.processor;

import java.util.Collection;

/** @author Shivaji */
public interface InputProcessor {

  void process();

  /**
   * Prints the outcoem of finding possible words for a given number
   *
   * @param number
   * @param results
   */
  default void printPossibleWords(String number, Collection<String> results) {
    if (!results.isEmpty()) {
      System.out.println("Possible words for Number --> " + number);
      results.stream().forEach(System.out::println);
    } else {
      System.out.println("No Possible words found for Number --> " + number);
    }
  }
}
