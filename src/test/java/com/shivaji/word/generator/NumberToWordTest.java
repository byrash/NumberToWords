package com.shivaji.word.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumberToWordTest {

  private NumberToWord objUnderTest;

  @BeforeEach
  void setUp() throws URISyntaxException {
    Path dictPath = Paths.get(NumberToWordTest.class.getResource("/test-dictionary.txt").toURI());
    objUnderTest = new NumberToWord(dictPath);
  }

  @Test
  void test_withNoDictValues() {
    assertEquals(
        0,
        objUnderTest.generateWords("3569377").size(),
        " I dont having mapping for Flowers in test dict");
  }

  @Test
  void test_withDictValues() {
    assertEquals(4, objUnderTest.generateWords("2255.63").size());
    assertEquals(
        "[BALLME, CALL-ME, CALLME, BALL-ME]", objUnderTest.generateWords("2255.63").toString());
  }

  @Test
  void test_withDictValuesAndMissingSingleDigit() {
    assertEquals(2, objUnderTest.generateWords("2255763").size());
    assertEquals("[BALL-7-ME, CALL-7-ME]", objUnderTest.generateWords("2255763").toString());
  }

  @Test
  void test_withDictValuesAndMissingMultipleDigits() {
    assertEquals(0, objUnderTest.generateWords("22557763").size());
    assertEquals("[]", objUnderTest.generateWords("22557763").toString());
  }

  @Test
  void test_withDictValuesAndMissingSingleDigitAtLast() {
    assertEquals(4, objUnderTest.generateWords("2255637").size());
    assertEquals(
        "[BALLME-7, CALLME-7, BALL-ME-7, CALL-ME-7]",
        objUnderTest.generateWords("2255637").toString());
  }
}
