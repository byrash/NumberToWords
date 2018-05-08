package com.shivaji;

import com.shivaji.commandline.processor.CommandLineArgsParser;
import com.shivaji.input.processor.CommandLineInputProcessor;
import com.shivaji.input.processor.FileInputProcessor;
import com.shivaji.input.processor.InputProcessor;
import com.shivaji.word.generator.NumberToWordsGenerator;
import java.util.logging.Logger;

/**
 * Main Program that boot straps the system.
 *
 * @author Shivaji Byrapaneni
 */
public class BootStrap {
  private static final Logger LOG = Logger.getLogger(BootStrap.class.getName());

  public static void main(String[] args) {
    // Init all objects required
    CommandLineArgsParser commandLineArgsParser = new CommandLineArgsParser(args);
    NumberToWordsGenerator numberToWordsGenerator =
        new NumberToWordsGenerator(commandLineArgsParser.getDictionary().get());
    InputProcessor commandLineInputProcessor =
        new CommandLineInputProcessor(numberToWordsGenerator);
    InputProcessor fileInputProcessor =
        new FileInputProcessor(commandLineArgsParser, numberToWordsGenerator);

    if (!commandLineArgsParser.getDictionary().isPresent()) {
      LOG.severe("System Cannot operate without dictionary file");
      System.exit(1);
    }
    if (commandLineArgsParser.getNumberFiles().isEmpty()) {
      // Command line
      commandLineInputProcessor.process();
    } else {
      // Input Files
      fileInputProcessor.process();
    }
    commandLineArgsParser.cleanUp();
  }
}
