package com.shivaji.output.processor;

/**
 * @author Shivaji Byrapaneni
 */
public class ConsoleStdOutImpl implements Output {

  @Override
  public void println(String outputLine) {
    System.out.println(outputLine);
  }
}
