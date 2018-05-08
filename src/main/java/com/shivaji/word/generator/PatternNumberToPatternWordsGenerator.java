package com.shivaji.word.generator;

import static com.shivaji.utility.CommonUtils.isEmpty;
import static java.text.MessageFormat.format;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Replaces pattern numbers eligible to be replaced with the assocaited patterns of words using
 * tailed recursion and produces the result for each number pattern
 *
 * @author Shivaji Byrapaneni
 */
public class PatternNumberToPatternWordsGenerator {

  private static final Logger LOG =
      Logger.getLogger(PatternNumberToPatternWordsGenerator.class.getName());
  public static final String REPLACE_ME = "REPLACE-ME";

  public static void go(
      final String pattern,
      final List<String> numsToReplace,
      final Map<String, Set<String>> replacements,
      final Collection<String> result) {
    LOG.fine(format("Generating Pattern Number to Pattern Words for [{0}]", pattern));
    List<String> _numsToReplace =
        numsToReplace.stream().filter(item -> !isEmpty.apply(item)).collect(Collectors.toList());
    String replacingNum = _numsToReplace.get(0);
    Set<String> replacementForNumber = replacements.get(replacingNum);
    if (_numsToReplace.size() > 1) {
      go(pattern, _numsToReplace.subList(1, _numsToReplace.size()), replacements, result);
    }
    generateWords(pattern, replacingNum, replacementForNumber, result);
  }

  private static void generateWords(
      final String pattern, String currentNum, Set<String> words, Collection<String> result) {
    Collection<String> curentResult = new HashSet<>();
    if (words == null || words.isEmpty()) {
      return;
    }
    words
        .stream()
        .forEach(
            word -> {
              result
                  .stream()
                  .forEach(
                      currentPattern -> {
                        curentResult.add(
                            getPatternAsString(currentPattern, currentNum)
                                .replaceAll(REPLACE_ME, word));
                      });
              if (result.isEmpty()) { // Root case where no results will exists
                curentResult.add(
                    getPatternAsString(pattern, currentNum).replaceAll(REPLACE_ME, word));
              }
            });
    result.clear();
    result.addAll(curentResult);
  }

  private static String getPatternAsString(String inputPattern, String numToReplace) {
    String pattern = new String(inputPattern);
    int lastIndexOf = pattern.lastIndexOf(numToReplace);
    int closingIndex = pattern.indexOf(lastIndexOf, ')');
    if (closingIndex == -1) {
      closingIndex = pattern.length();
    }
    pattern =
        pattern.substring(0, lastIndexOf)
            + REPLACE_ME
            + pattern.substring(lastIndexOf, closingIndex);
    return pattern.replaceAll(REPLACE_ME + numToReplace, REPLACE_ME);
  }
}
