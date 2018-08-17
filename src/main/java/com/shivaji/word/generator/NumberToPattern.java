package com.shivaji.word.generator;

import static com.shivaji.utility.CommonUtils.isEmpty;
import static com.shivaji.utility.CommonUtils.join;
import static com.shivaji.utility.Constants.DIGIT_END;
import static com.shivaji.utility.Constants.DIGIT_START;
import static java.text.MessageFormat.format;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Takes a number and returns a set of possible combinations for a given number
 *
 * @author Shivaji Byrapaneni
 */
public class NumberToPattern {

  private static final Logger LOG = Logger.getLogger(NumberToPattern.class.getName());

  /**
   * Gets all the possible combinations for a given number matching the requirement that if no match
   * can be made, a single digit can be left as is at that point. No two consecutive digits can
   * remain unchanged and the program should skip over a number (producing no output) if a match
   * cannot be made.
   *
   * <p>Algorithm uses recursion, this function choose a pivot starting from position 1 of the given
   * number and splits the number to left and right of pivot ( pivot included in right ) and calls
   * the same function recursively with left and then right. This generateWords contineous until
   * recursive function yields values, where the values include the number itself which is a single
   * digit and the same number as string string { I'm differentiating non considerable number
   * placing it with in brackets '()' }. Then the results in each recursion generateWords are
   * cartisian joined with the counter part i.e. left to right. This generateWords contineous until
   * and final result is made and final result removes all the non replacable numbers with more than
   * one characters as requirements states no consecutive numbers should not be remain changed.
   *
   * @param number
   * @return
   */
  public Collection<String> generatePossiblePatterns(String number) {
    LOG.fine(format("Processing Number [{0}]", number));
    Stream<String> combinationForCurrentNumber =
        Stream.of(
            number,
            join(DIGIT_START, number, DIGIT_END),
            join(DIGIT_START, DIGIT_END, number),
            join(number, DIGIT_START, DIGIT_END));
    if (isEmpty.apply(number) || number.length() == 1) {
      LOG.fine(format("**** Yielding ****"));
      return combinationForCurrentNumber.collect(Collectors.toSet());
    }
    Collection<String> results = new HashSet<>();
    combinationForCurrentNumber.forEach(results::add);
    IntStream.range(1, number.length())
        .forEach(
            index -> {
              String left = number.substring(0, index);
              LOG.fine(format("Left [{0}]", left));
              Collection<String> leftNumPossibilities = generatePossiblePatterns(left);
              String right = number.substring(index);
              LOG.fine(format("Right [{0}]", right));
              Collection<String> rightNumPossibilities = generatePossiblePatterns(right);
              results.addAll(generateCombinations(leftNumPossibilities, rightNumPossibilities));
            });
    return clearUnchangeableTwoConsecutiveItemsAndUnUseful(results);
  }

  private Set<String> clearUnchangeableTwoConsecutiveItemsAndUnUseful(Collection<String> results) {
    return results
        .stream()
        .filter(
            item -> {
              int openingIndex = item.indexOf(DIGIT_START);
              int closingIndex = item.indexOf(DIGIT_END);
              boolean isNumberFieldExists = openingIndex != -1 && closingIndex != -1;
              return !((isNumberFieldExists
                      && item.substring(openingIndex + 1, closingIndex).length() > 1)
                  || item.contains(join(DIGIT_END, DIGIT_START)));
            })
        .map(
            item -> {
              String tmp = item;
              String emptyDigit = join(DIGIT_START, DIGIT_END);
              if (tmp.startsWith(emptyDigit)) {
                tmp = item.substring(2);
              }
              if (tmp.endsWith(emptyDigit)) {
                tmp = tmp.substring(0, tmp.length() - 2);
              }
              return tmp;
            })
        .collect(Collectors.toSet());
  }

  private Collection<String> generateCombinations(
      Collection<String> left, Collection<String> right) {
    Collection<String> combinations = new HashSet<>();
    left.stream()
        .forEach(
            leftStr ->
                right.stream().forEach(rightStr -> combinations.add(join(leftStr, rightStr))));
    return combinations;
  }
}
