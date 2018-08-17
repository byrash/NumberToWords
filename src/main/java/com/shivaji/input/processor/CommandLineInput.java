package com.shivaji.input.processor;

import static java.text.MessageFormat.format;

import com.shivaji.word.generator.NumberToWord;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Processes the given number from command line and prints them onto console
 *
 * @author Shivaji Byrapaneni
 */
public class CommandLineInput extends Input {
  private static final Logger LOG = Logger.getLogger(CommandLineInput.class.getName());
  private final NumberToWord numberToWord;

  public CommandLineInput(NumberToWord numberToWord) {
    super();
    this.numberToWord = numberToWord;
  }

  public void generateWords() {
    LOG.info("<!---------------------------------------------!>");
    LOG.info("Use exit and hit enter to exit the program");
    LOG.info("<!---------------------------------------------!>");
    Scanner scan = new Scanner(System.in);
    String inputNum = null;
    while (!isExit(inputNum)) {
      try {
        inputNum = scan.nextLine();
        if (!isExit(inputNum)) {
          generateWordsAndPrint(numberToWord, inputNum);
        }
      } catch (Exception e) {
        LOG.severe(format("Command line Failed with [{0}]", e.getMessage()));
        System.exit(1);
      }
    }
  }

  private static boolean isExit(String input) {
    return input != null && !input.isEmpty() && input.equalsIgnoreCase("exit");
  }
}
