package com.shivaji.word.generator;

import static com.shivaji.utility.Constants.NOTHING;
import static java.text.MessageFormat.format;

import com.shivaji.dict.DictionaryFileProcessor;
import com.shivaji.dict.DictionaryVo;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * For a given number produces all the possible words outcomes
 *
 * <p>This is the main class which utilises NumberToPossibleCombinationsGenerator and
 * PatternNumberToPatternWordsGenerator classes to generate teh final results
 *
 * @author Shivaji Byrapaneni
 */
public class NumberToWordsGenerator {

  private static final Logger LOG = Logger.getLogger(NumberToWordsGenerator.class.getName());
  public static final String NON_NUMERIC_REGEX = "[^0-9]";
  public static final String NUM_TO_WORD_SEPERATOR = "-";
  private final DictionaryVo dictionary;
  private final NumberToPossibleCombinationsGenerator numberToPossibleCombinationsGenerator;

  public NumberToWordsGenerator(Path dictionaryFilePath) {
    Optional<DictionaryVo> dict = DictionaryFileProcessor.process(dictionaryFilePath);
    dict.orElseThrow(() -> new IllegalStateException("System cannot operate without dictionary"));
    this.dictionary = dict.get();
    numberToPossibleCombinationsGenerator = new NumberToPossibleCombinationsGenerator();
  }

  public Collection<String> go(String number) {
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
    Optional<Collection<String>> fullMatch = dictionary.getMatchedWords(cleanedNumber);
    // Full match found so nothing else to do just return those values if not get pattern matched
    // values
    return prependHoldIfapplicable(
        fullMatch.isPresent()
            ? fullMatch.get().stream().map(String::toUpperCase).collect(Collectors.toSet())
            : getNumPatternMatchingResults(cleanedNumber),
        hold);
  }

  private Collection<String> prependHoldIfapplicable(Collection<String> items, String hold) {
    if (hold != null && !hold.isEmpty()) {
      return items
          .stream()
          .map(item -> hold.concat(NUM_TO_WORD_SEPERATOR).concat(item))
          .collect(Collectors.toSet());
    }
    return items;
  }

  private Collection<String> getNumPatternMatchingResults(String cleanedNumber) {
    Collection<String> possibleNumberMatchesToConsider =
        numberToPossibleCombinationsGenerator.process(cleanedNumber);
    possibleNumberMatchesToConsider.remove(cleanedNumber); // No need to consider full match
    LOG.fine(format("Found [{0}] patterns to consider", possibleNumberMatchesToConsider.size()));
    LOG.fine(format("Number patterns to consider {0}", possibleNumberMatchesToConsider));
    final Collection<String> subStringMatches = new HashSet<>();

    possibleNumberMatchesToConsider.forEach(
        patternNum -> {
          List<String> numsInPatternToReplace =
              Arrays.stream(patternNum.split("\\(.*?\\)")).collect(Collectors.toList());
          Map<String, Optional<Collection<String>>> numToWords =
              getDictWordsForNumber(numsInPatternToReplace.stream().collect(Collectors.toSet()));
          // If any number is not replacable then do not process
          // i.e. Only process the number if all words exists
          if (isAllNumbersHaveWords(numToWords)) {
            final Map<String, Set<String>> wordsToReplace = new HashMap<>();
            numToWords.forEach(
                (k, v) -> {
                  wordsToReplace.put(
                      k, (Set<String>) v.get()); // As already asserted as exits so safe to do get
                });
            HashSet<String> thisPatternResult = new HashSet<>();
            PatternNumberToPatternWordsGenerator.go(
                patternNum, numsInPatternToReplace, wordsToReplace, thisPatternResult);
            subStringMatches.addAll(thisPatternResult);
          }
        });
    return subStringMatches
        .stream()
        .map(
            item -> {
              String str =
                  item.replaceAll("\\(", NUM_TO_WORD_SEPERATOR)
                      .replaceAll("\\)", NUM_TO_WORD_SEPERATOR);
              int lastIndexOf = str.lastIndexOf(NUM_TO_WORD_SEPERATOR);
              if (lastIndexOf != -1 && lastIndexOf + 1 == str.length()) {
                str = str.substring(0, lastIndexOf);
              }
              return str.toUpperCase();
            })
        .map(item -> item.replaceAll("[" + NUM_TO_WORD_SEPERATOR + "]{2,}", NUM_TO_WORD_SEPERATOR))
        .collect(Collectors.toSet());
  }

  private Map<String, Optional<Collection<String>>> getDictWordsForNumber(
      Set<String> numsInPatternToReplace) {
    Map<String, Optional<Collection<String>>> numToWords =
        new HashMap<>(numsInPatternToReplace.size());

    numsInPatternToReplace.forEach(num -> numToWords.put(num, dictionary.getMatchedWords(num)));
    return numToWords;
  }

  private Boolean isAllNumbersHaveWords(
      Map<String, Optional<Collection<String>>> individualNumberToWords) {
    AtomicBoolean isAllNumbersHaveWords = new AtomicBoolean(true);
    individualNumberToWords.forEach(
        (k, v) -> {
          if (!v.isPresent()) {
            isAllNumbersHaveWords.set(false);
          }
        });
    return isAllNumbersHaveWords.get();
  }
}
