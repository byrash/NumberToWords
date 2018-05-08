package com.shivaji.input.processor;

import static java.text.MessageFormat.format;

import com.shivaji.commandline.processor.CommandLineArgsParser;
import com.shivaji.word.generator.NumberToWordsGenerator;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Processes the given number input file and prints the results onto console
 *
 * @author Shivaji Byrapaneni
 */
public class FileInputProcessor implements InputProcessor {
  private static final Logger LOG = Logger.getLogger(FileInputProcessor.class.getName());
  private final CommandLineArgsParser commandLineArgsParser;
  private final NumberToWordsGenerator numberToWordsGenerator;

  public FileInputProcessor(
      CommandLineArgsParser commandLineArgsParser, NumberToWordsGenerator numberToWordsGenerator) {
    super();
    this.commandLineArgsParser = commandLineArgsParser;
    this.numberToWordsGenerator = numberToWordsGenerator;
  }

  public void process() {
    commandLineArgsParser
        .getNumberFiles()
        .forEach(
            filePath -> {
              try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                reader
                    .lines()
                    .forEach(
                        line -> {
                          Collection<String> go = numberToWordsGenerator.go(line);
                          printPossibleWords(line, go);
                        });
              } catch (IOException e) {
                LOG.warning(
                    format(
                        "Unable to process a file [{0}] due to [{1}]", filePath, e.getMessage()));
                // Let system continue with next file
              }
            });
  }
}
