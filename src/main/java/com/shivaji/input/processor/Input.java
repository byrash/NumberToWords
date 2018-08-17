package com.shivaji.input.processor;

import com.shivaji.output.processor.Output;
import com.shivaji.word.generator.NumberToWord;
import java.util.Collection;

/** @author Shivaji */
public abstract class Input {

  public abstract void generateWordsAndPrint(Output outputPrinter);

  /** Prints the outcome of finding possible words for a given number */
  protected void generateWordsAndPrint(
      NumberToWord numberToWord, String number, Output outputPrinter) {
    Collection<String> results = numberToWord.generateWords(number);
    if (!results.isEmpty()) {
      outputPrinter.println("Possible words for Number --> " + number);
      results.forEach(outputPrinter::println);
    } else {
      outputPrinter.println("No Possible words found for Number --> " + number);
    }
  }
}
