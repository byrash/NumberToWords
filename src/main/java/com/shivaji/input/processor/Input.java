package com.shivaji.input.processor;

import com.shivaji.word.generator.NumberToWord;
import java.util.Collection;

/** @author Shivaji */
public abstract class Input {

  public abstract void generateWords();

  /** Prints the outcome of finding possible words for a given number */
  protected void generateWordsAndPrint(NumberToWord numberToWord, String number) {
    Collection<String> results = numberToWord.generateWords(number);
    if (!results.isEmpty()) {
      System.out.println("Possible words for Number --> " + number);
      results.stream().forEach(System.out::println);
    } else {
      System.out.println("No Possible words found for Number --> " + number);
    }
  }
}
