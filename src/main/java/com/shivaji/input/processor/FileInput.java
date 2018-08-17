package com.shivaji.input.processor;

import static java.text.MessageFormat.format;

import com.shivaji.commandline.processor.CommandLineArg;
import com.shivaji.word.generator.NumberToWord;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Processes the given number input file and prints the results onto console
 *
 * @author Shivaji Byrapaneni
 */
public class FileInput extends Input {
  private static final Logger LOG = Logger.getLogger(FileInput.class.getName());
  private final CommandLineArg commandLineArg;
  private final NumberToWord numberToWord;

  public FileInput(CommandLineArg commandLineArg, NumberToWord numberToWord) {
    super();
    this.commandLineArg = commandLineArg;
    this.numberToWord = numberToWord;
  }

  public void generateWords() {
    commandLineArg.getInputNumFiles().forEach(this::generateWordsForInputFile);
  }

  private void generateWordsForInputFile(Path filePath) {
    try (BufferedReader reader = Files.newBufferedReader(filePath)) {
      reader
          .lines()
          .forEach(fileInputNumber -> generateWordsAndPrint(numberToWord, fileInputNumber));
    } catch (IOException e) {
      LOG.warning(
          format("Unable to generateWords a file [{0}] due to [{1}]", filePath, e.getMessage()));
      // Let system continue with next file
    }
  }
}
