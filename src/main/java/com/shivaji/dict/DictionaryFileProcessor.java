package com.shivaji.dict;

import static com.shivaji.utility.CommonUtils.isNotEmpty;
import static com.shivaji.utility.Constants.NOTHING;
import static java.text.MessageFormat.format;

import com.shivaji.commandline.processor.CommandLineArgsParser;
import com.shivaji.utility.CharToNum;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Processes the dictionary file and supplies with a dictionary Vo
 *
 * @author Shivaji Byrapaneni
 */
public class DictionaryFileProcessor {

  private static final Logger LOG = Logger.getLogger(CommandLineArgsParser.class.getName());
  public static final String NON_ALPHABETS_REGEX = "[^a-zA-Z]";

  /**
   * Take a path to dict file and generates dict vo By the time it reaches here expecting file
   * assertion is done
   *
   * @return
   */
  public static Optional<DictionaryVo> process(Path dictPath) {
    if (null == dictPath) {
      return Optional.empty();
    }
    DictionaryVo dictionaryVo = new DictionaryVo();
    try (BufferedReader reader = Files.newBufferedReader(dictPath)) {
      reader
          .lines()
          .forEach(
              line -> {
                LOG.fine(format("Working on Line [{0}]", line));
                // Clean data
                String cleanedLine = line.replaceAll(NON_ALPHABETS_REGEX, NOTHING);
                LOG.fine(format("Working on Cleaned Line [{0}]", cleanedLine));
                // map residual data to a number
                final StringBuilder number = new StringBuilder();
                final char[] chars = cleanedLine.toCharArray();
                IntStream.range(0, chars.length)
                    .forEach(
                        i -> {
                          // Cant expect to receive 0 here
                          number.append(CharToNum.getNumFromChar(chars[i]));
                        });
                String finalNum = number.toString();
                LOG.fine(format("Mapped number for line [{0}] is [{1}]", line, finalNum));
                // Check if the number is already existing in the dict if yes add teh word ( un
                // Cleaned ) to the number to words collection or else create a new entry
                if (isNotEmpty.apply(line)
                    && isNotEmpty.apply(finalNum)
                    && isNotEmpty.apply(cleanedLine)) {
                  dictionaryVo.addToDict(finalNum, line);
                }
              });
    } catch (IOException e) {
      LOG.warning(format("Unable to process a dict file due to [{0}]", e.getMessage()));
      return Optional.empty();
    }
    return Optional.of(dictionaryVo);
  }
}
