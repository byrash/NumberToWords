package com.shivaji;

import com.shivaji.commandline.processor.CommandLineArg;
import com.shivaji.input.processor.CommandLineInput;
import com.shivaji.input.processor.FileInput;
import com.shivaji.input.processor.Input;
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

      NumberToWord numberToWord = new NumberToWord(commandLineArg.getDictionary());
      Input commandLineInput = new CommandLineInput(numberToWord);
      Input fileInput = new FileInput(commandLineArg, numberToWord);

      if (commandLineArg.getInputNumFiles().isEmpty()) {
        // Command line
        commandLineInput.generateWords();
      } else {
        // Input Files
        fileInput.generateWords();
      }
    }
  }
}
