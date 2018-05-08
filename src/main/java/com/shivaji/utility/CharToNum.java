package com.shivaji.utility;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;

/**
 * Returns a number for the associated character
 *
 * @author Shivaji
 */
public enum CharToNum {
  ABC(2, 'A', 'B', 'C'),
  DEF(3, 'D', 'E', 'F'),
  GHI(4, 'G', 'H', 'I'),
  JKL(5, 'J', 'K', 'L'),
  MNO(6, 'M', 'N', 'O'),
  PQRS(7, 'P', 'Q', 'R', 'S'),
  TUV(8, 'T', 'U', 'V'),
  WXYZ(9, 'W', 'X', 'Y', 'Z');

  CharToNum(int num, Character... chars) {
    this.number = num;
    this.characters = Arrays.asList(chars);
  }

  private int number;
  private Collection<Character> characters;

  /**
   * Finds if the character supplied matched any and if yes returns the associated number
   *
   * @param ch
   * @return
   */
  public static int getNumFromChar(char ch) {
    Optional<CharToNum> matchedItem =
        EnumSet.allOf(CharToNum.class)
            .stream()
            .filter(e -> e.characters.contains(Character.toUpperCase(ch)))
            .findFirst();
    if (matchedItem.isPresent()) {
      return matchedItem.get().number;
    }
    return 0;
  }
}
