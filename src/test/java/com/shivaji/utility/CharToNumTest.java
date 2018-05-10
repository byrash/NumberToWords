package com.shivaji.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** @author Shivaji */
class CharToNumTest {

  @Test
  void getNumFromChar_ABC() {
    assertEquals(2, CharToNum.getNumFromChar('a'));
    assertEquals(2, CharToNum.getNumFromChar('A'));
    assertEquals(2, CharToNum.getNumFromChar('b'));
    assertEquals(2, CharToNum.getNumFromChar('B'));
    assertEquals(2, CharToNum.getNumFromChar('c'));
    assertEquals(2, CharToNum.getNumFromChar('C'));
  }

  @Test
  void getNumFromChar_DEF() {
    assertEquals(3, CharToNum.getNumFromChar('d'));
    assertEquals(3, CharToNum.getNumFromChar('D'));
    assertEquals(3, CharToNum.getNumFromChar('e'));
    assertEquals(3, CharToNum.getNumFromChar('E'));
    assertEquals(3, CharToNum.getNumFromChar('f'));
    assertEquals(3, CharToNum.getNumFromChar('F'));
  }

  @Test
  void getNumFromChar_GHI() {
    assertEquals(4, CharToNum.getNumFromChar('g'));
    assertEquals(4, CharToNum.getNumFromChar('G'));
    assertEquals(4, CharToNum.getNumFromChar('h'));
    assertEquals(4, CharToNum.getNumFromChar('H'));
    assertEquals(4, CharToNum.getNumFromChar('i'));
    assertEquals(4, CharToNum.getNumFromChar('I'));
  }

  @Test
  void getNumFromChar_JKL() {
    assertEquals(5, CharToNum.getNumFromChar('j'));
    assertEquals(5, CharToNum.getNumFromChar('J'));
    assertEquals(5, CharToNum.getNumFromChar('k'));
    assertEquals(5, CharToNum.getNumFromChar('K'));
    assertEquals(5, CharToNum.getNumFromChar('l'));
    assertEquals(5, CharToNum.getNumFromChar('L'));
  }

  @Test
  void getNumFromChar_MNO() {
    assertEquals(6, CharToNum.getNumFromChar('m'));
    assertEquals(6, CharToNum.getNumFromChar('M'));
    assertEquals(6, CharToNum.getNumFromChar('n'));
    assertEquals(6, CharToNum.getNumFromChar('N'));
    assertEquals(6, CharToNum.getNumFromChar('o'));
    assertEquals(6, CharToNum.getNumFromChar('O'));
  }

  @Test
  void getNumFromChar_PQRS() {
    assertEquals(7, CharToNum.getNumFromChar('p'));
    assertEquals(7, CharToNum.getNumFromChar('P'));
    assertEquals(7, CharToNum.getNumFromChar('q'));
    assertEquals(7, CharToNum.getNumFromChar('Q'));
    assertEquals(7, CharToNum.getNumFromChar('r'));
    assertEquals(7, CharToNum.getNumFromChar('R'));
    assertEquals(7, CharToNum.getNumFromChar('s'));
    assertEquals(7, CharToNum.getNumFromChar('S'));
  }

  @Test
  void getNumFromChar_TUV() {
    assertEquals(8, CharToNum.getNumFromChar('t'));
    assertEquals(8, CharToNum.getNumFromChar('T'));
    assertEquals(8, CharToNum.getNumFromChar('u'));
    assertEquals(8, CharToNum.getNumFromChar('U'));
    assertEquals(8, CharToNum.getNumFromChar('v'));
    assertEquals(8, CharToNum.getNumFromChar('V'));
  }

  @Test
  void getNumFromChar_WXYZ() {
    assertEquals(9, CharToNum.getNumFromChar('w'));
    assertEquals(9, CharToNum.getNumFromChar('W'));
    assertEquals(9, CharToNum.getNumFromChar('x'));
    assertEquals(9, CharToNum.getNumFromChar('X'));
    assertEquals(9, CharToNum.getNumFromChar('y'));
    assertEquals(9, CharToNum.getNumFromChar('Y'));
    assertEquals(9, CharToNum.getNumFromChar('z'));
    assertEquals(9, CharToNum.getNumFromChar('Z'));
  }

  @Test
  void getNumFromChar_InvalidChar() {
    assertEquals(0, CharToNum.getNumFromChar('~'));
  }
}
