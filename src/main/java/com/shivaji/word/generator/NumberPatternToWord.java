package com.shivaji.word.generator;

import static com.shivaji.utility.CommonUtils.isEmpty;
import static com.shivaji.utility.CommonUtils.join;
import static com.shivaji.utility.Constants.DIGIT_END;
import static com.shivaji.utility.Constants.DIGIT_START;
import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Replaces pattern numbers eligible to be replaced with the associated patterns of words using
 * tailed recursion and produces the result for each number pattern
 *
 * @author Shivaji Byrapaneni
 */
class NumberPatternToWord {

  private NumberPatternToWord() {}

  private static final Logger LOG = Logger.getLogger(NumberPatternToWord.class.getName());
  private static final String REPLACE_ME = "REPLACE-ME";

  static void generateWordsForNumberPattern(
      final String pattern,
      final List<String> numsToReplace,
      final Map<String, Set<String>> replacements,
      final Collection<String> result) {
    LOG.fine(format("Generating Pattern Number to Pattern Words for [{0}]", pattern));
    List<String> tempNumsToReplace =
        numsToReplace.stream().filter(item -> !isEmpty.apply(item)).collect(Collectors.toList());
    String replacingNum = tempNumsToReplace.get(0);
    Set<String> replacementForNumber = replacements.get(replacingNum);
    if (tempNumsToReplace.size() > 1) {
      generateWordsForNumberPattern(
          pattern, tempNumsToReplace.subList(1, tempNumsToReplace.size()), replacements, result);
    }
    generateWords(pattern, replacingNum, replacementForNumber, result);
  }

  private static void generateWords(
      final String pattern, String currentNum, Set<String> words, Collection<String> result) {
    Collection<String> currentResult = new HashSet<>();
    if (words == null || words.isEmpty()) {
      return;
    }
    words.forEach(
        word -> {
          result.forEach(
              currentPattern ->
                  currentResult.add(
                      getPatternAsString(currentPattern, currentNum).replaceAll(REPLACE_ME, word)));
          if (result.isEmpty()) { // Root case where no results will exists
            currentResult.add(getPatternAsString(pattern, currentNum).replaceAll(REPLACE_ME, word));
          }
        });
    result.clear();
    result.addAll(currentResult);
  }

  private static String getPatternAsString(String inputPattern, String numToReplace) {
    String[] split = inputPattern.split("\\(");
    List<String> patternItems = new ArrayList<>();
    List<String> collect =
        Arrays.stream(split)
            .map(
                item -> {
                  if (item.contains(DIGIT_END)) {
                    return DIGIT_START.concat(item);
                  }
                  return item;
                })
            .collect(Collectors.toList());
    collect.forEach(
        item -> {
          int start = item.indexOf(DIGIT_START);
          int end = item.indexOf(DIGIT_END);
          if (-1 != start && -1 != end) {
            patternItems.add(join(item.substring(start, end), DIGIT_END));
            if (end + 1 < item.length()) {
              patternItems.add(item.substring(end + 1));
            }
          } else {
            patternItems.add(item);
          }
        });

    for (int i = patternItems.size() - 1; i >= 0; i--) {
      if (patternItems.get(i).equalsIgnoreCase(numToReplace)) {
        patternItems.set(i, REPLACE_ME);
        break;
      }
    }
    Optional<String> s = patternItems.stream().reduce(String::concat);
    return s.orElse("");
  }
}
