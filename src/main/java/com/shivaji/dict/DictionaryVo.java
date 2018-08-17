package com.shivaji.dict;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Data holder of processed dict
 *
 * @author Shivaji Byrapaneni
 */
public class DictionaryVo {

  private Map<String, Collection<String>> numberToWords = new HashMap<>();

  public Map<String, Collection<String>> getNumberToWords() {
    return numberToWords;
  }

  /**
   * Checks if the given number is laredy on dict if not adds it and adds teh word or else just add
   * the word.
   *
   * @param number
   * @param word
   */
  public void addToDict(String number, String word) {
    if (!this.numberToWords.containsKey(number)) {
      this.numberToWords.put(number, new HashSet<>());
    }
    this.numberToWords.get(number).add(word);
  }

  public Collection<String> getMatchedWords(String number) {
    Collection<String> value = this.numberToWords.get(number);
    return null != value ? value : Collections.emptySet();
  }
}
