package com.shivaji.word.generator;

import static com.shivaji.utility.Constants.NOTHING;
import static java.text.MessageFormat.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

import com.shivaji.dict.DictionaryFile;
import com.shivaji.dict.DictionaryVo;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * For a given number produces all the possible words outcomes
 *
 * <p>This is the main class which utilises NumberToPattern and NumberPatternToWord classes to
 * generate teh final results
 *
 * @author Shivaji Byrapaneni
 */
public class NumberToWord {

  private static final Logger LOG = Logger.getLogger(NumberToWord.class.getName());
  private static final String NON_NUMERIC_REGEX = "[^0-9]";
  private static final String NUM_TO_WORD_SEPARATOR = "-";
  private final DictionaryVo dictionary;
  private final NumberToPattern numberToPattern;

  public NumberToWord(Path dictionaryFilePath) {
    this.dictionary = DictionaryFile.load(dictionaryFilePath);
    numberToPattern = new NumberToPattern();
  }

  public Collection<String> generateWords(String number) {
    LOG.fine(format("Generating Words for input number [{0}]", number));
    String cleanedNumber = number.replaceAll(NON_NUMERIC_REGEX, NOTHING);
    String hold = null;
    if (cleanedNumber.startsWith("1800") || cleanedNumber.startsWith("1300")) {
      hold = cleanedNumber.substring(0, 4);
      cleanedNumber = cleanedNumber.substring(4);
      LOG.fine(
          format(
              "Number supplied [{0}] starts with 1800 or 1300 so considering post 1800 or 1300 only [{1}]",
              number, cleanedNumber));
    }
    LOG.fine(format("Generating Words for cleaned input number [{0}]", cleanedNumber));
    Collection<String> results = dictionary.getMatchedWords(cleanedNumber);
    // Full match found so nothing else to do just return those values if not get pattern matched
    // values
    if (results.isEmpty()) {
      results = generateWordsMatchingPatterns(cleanedNumber);
    } else {
      results = results.stream().map(String::toUpperCase).collect(toSet());
    }
    return prependHoldIfApplicable(results, hold);
  }

  private Collection<String> prependHoldIfApplicable(Collection<String> items, String hold) {
    if (hold != null && !hold.isEmpty()) {
      return items
          .stream()
          .map(item -> hold.concat(NUM_TO_WORD_SEPARATOR).concat(item))
          .collect(toSet());
    }
    return items;
  }

  private Collection<String> generateWordsMatchingPatterns(String cleanedNumber) {
    Collection<String> possibleNumberMatchesToConsider =
        numberToPattern.generatePossiblePatterns(cleanedNumber);
    possibleNumberMatchesToConsider.remove(cleanedNumber); // No need to consider full match
    LOG.fine(format("Found [{0}] patterns to consider", possibleNumberMatchesToConsider.size()));
    LOG.fine(format("Number patterns to consider {0}", possibleNumberMatchesToConsider));
    final Collection<String> subStringMatches = new HashSet<>();

    possibleNumberMatchesToConsider.forEach(
        patternNum -> {
          List<String> numsInPatternToReplace =
              stream(patternNum.split("\\(.*?\\)")).collect(Collectors.toList());
          Map<String, Collection<String>> numToWords =
              getDictWordsForNumber(new HashSet<>(numsInPatternToReplace));
          // If any number is not replacable then do not generateWordsAndPrint
          // i.e. Only generateWordsAndPrint the number if all words exists
          if (isAllNumbersHaveWords(numToWords)) {
            final Map<String, Set<String>> wordsToReplace = new HashMap<>();
            numToWords.forEach(
                (k, v) ->
                    wordsToReplace.put(
                        k, (Set<String>) v) // As already asserted as exits so safe to do get
                );
            HashSet<String> thisPatternResult = new HashSet<>();
            NumberPatternToWord.generateWordsForNumberPattern(
                patternNum, numsInPatternToReplace, wordsToReplace, thisPatternResult);
            subStringMatches.addAll(thisPatternResult);
          }
        });
    return subStringMatches
        .stream()
        .map(
            item -> {
              String str =
                  item.replaceAll("\\(", NUM_TO_WORD_SEPARATOR)
                      .replaceAll("\\)", NUM_TO_WORD_SEPARATOR);
              int lastIndexOf = str.lastIndexOf(NUM_TO_WORD_SEPARATOR);
              if (lastIndexOf != -1 && lastIndexOf + 1 == str.length()) {
                str = str.substring(0, lastIndexOf);
              }
              return str.toUpperCase();
            })
        .map(item -> item.replaceAll("[" + NUM_TO_WORD_SEPARATOR + "]{2,}", NUM_TO_WORD_SEPARATOR))
        .collect(toSet());
  }

  private Map<String, Collection<String>> getDictWordsForNumber(
      Set<String> numsInPatternToReplace) {
    Map<String, Collection<String>> numToWords = new HashMap<>(numsInPatternToReplace.size());
    numsInPatternToReplace.forEach(num -> numToWords.put(num, dictionary.getMatchedWords(num)));
    return numToWords;
  }

  private Boolean isAllNumbersHaveWords(Map<String, Collection<String>> individualNumberToWords) {
    AtomicBoolean isAllNumbersHaveWords = new AtomicBoolean(true);
    individualNumberToWords.forEach(
        (k, v) -> {
          if (v.isEmpty()) {
            isAllNumbersHaveWords.set(false);
          }
        });
    return isAllNumbersHaveWords.get();
  }
}
