package com.shivaji.input.processor;

import static java.text.MessageFormat.format;

import com.shivaji.word.generator.NumberToWordsGenerator;
import java.util.Collection;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Processes teh given number number from command line and prints them onto console
 *
 * @author Shivaji Byrapaneni
 */
public class CommandLineInputProcessor implements InputProcessor {
  private static final Logger LOG = Logger.getLogger(CommandLineInputProcessor.class.getName());
  private final NumberToWordsGenerator numberToWordsGenerator;

  public CommandLineInputProcessor(NumberToWordsGenerator numberToWordsGenerator) {
    super();
    this.numberToWordsGenerator = numberToWordsGenerator;
  }

  public void process() {
    LOG.info("<!---------------------------------------------!>");
    LOG.info("Use exit and hit enter to exit the program");
    LOG.info("<!---------------------------------------------!>");
    Scanner scan = new Scanner(System.in);
    String input = null;
    while (!isExit(input)) {
      try {
        input = scan.nextLine();
        if (!isExit(input)) {
          Collection<String> go = numberToWordsGenerator.go(input);
          printPossibleWords(input, go);
        }
      } catch (Throwable e) {
        LOG.severe(format("Command line Failed with [{0}]", e.getMessage()));
        System.exit(1);
      }
    }
  }

  private static boolean isExit(String input) {
    return input != null && !input.isEmpty() && input.equalsIgnoreCase("exit");
  }
}
