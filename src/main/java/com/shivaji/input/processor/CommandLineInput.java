package com.shivaji.input.processor;

import static java.text.MessageFormat.format;

import com.shivaji.output.processor.Output;
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

  public void generateWordsAndPrint(Output outputPrinter) {
    LOG.info("<!---------------------------------------------!>");
    LOG.info("Use exit and hit enter to exit the program");
    LOG.info("<!---------------------------------------------!>");
    Scanner scan = new Scanner(System.in);
    String inputNum = null;
    while (isNotBlank(inputNum) && isNotExit(inputNum)) {
      try {
        inputNum = scan.nextLine();
        if (isNotExit(inputNum)) {
          generateWordsAndPrint(numberToWord, inputNum, outputPrinter);
        }
      } catch (Exception e) {
        LOG.severe(format("Command line Failed with [{0}]", e.getMessage()));
        System.exit(1);
      }
    }
  }

  private static boolean isNotBlank(String input) {
    return input != null && !input.isEmpty();
  }

  private static boolean isNotExit(String input) {
    return !input.equalsIgnoreCase("exit");
  }
}
