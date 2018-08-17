package com.shivaji;

import com.shivaji.commandline.processor.CommandLineArg;
import com.shivaji.input.processor.CommandLineInput;
import com.shivaji.input.processor.FileInput;
import com.shivaji.input.processor.Input;
import com.shivaji.output.processor.ConsoleStdOutImpl;
import com.shivaji.word.generator.NumberToWord;

/**
 * Main Program that boot straps the system.
 *
 * @author Shivaji Byrapaneni
 */
public class BootStrap {

  public static void main(String[] args) {
    // Init all objects required
    try (CommandLineArg commandLineArg = new CommandLineArg(args)) {

      // Main class that does the number to word generation
      NumberToWord numberToWord = new NumberToWord(commandLineArg.getDictionary());

      if (commandLineArg.getInputNumFiles().isEmpty()) {
        // Command line
        handleCmdLineInputNumToWords(numberToWord);
      } else {
        // Input Files
        handleFileInputNumToWords(commandLineArg, numberToWord);
      }
    }
  }

  private static void handleFileInputNumToWords(
      CommandLineArg commandLineArg, NumberToWord numberToWord) {
    Input fileInput = new FileInput(commandLineArg, numberToWord);
    fileInput.generateWordsAndPrint(new ConsoleStdOutImpl());
  }

  private static void handleCmdLineInputNumToWords(NumberToWord numberToWord) {
    Input commandLineInput = new CommandLineInput(numberToWord);
    commandLineInput.generateWordsAndPrint(new ConsoleStdOutImpl());
  }
}
