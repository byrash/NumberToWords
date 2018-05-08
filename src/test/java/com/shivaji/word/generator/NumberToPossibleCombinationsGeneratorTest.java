package com.shivaji.word.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shivaji.word.generator.NumberToPossibleCombinationsGenerator;
import java.util.Collection;
import org.junit.jupiter.api.Test;

/** @author Shivaji */
class NumberToPossibleCombinationsGeneratorTest {
  NumberToPossibleCombinationsGenerator objectUnderTest = new NumberToPossibleCombinationsGenerator();

  @Test
  void go_12() {
    Collection<String> results = objectUnderTest.process("12");
    assertEquals(3, results.size());
    assertEquals(
        "[(1)2, 12, 1(2)]",
        results.toString());
  }

  @Test
  void go_123() {
    Collection<String> results = objectUnderTest.process("123");
    assertEquals(5, results.size());
    assertEquals("[1(2)3, 123, (1)2(3), 12(3), (1)23]", results.toString());
  }

  @Test
  void go_12345() {
    Collection<String> results = objectUnderTest.process("12345");
    assertEquals(13, results.size());
    assertEquals(
        "[(1)2(3)4(5), 1(2)345, (1)2(3)45, 1(2)3(4)5, (1)23(4)5, (1)234(5), 12(3)4(5), (1)2345, 123(4)5, 1234(5), 1(2)34(5), 12(3)45, 12345]",
        results.toString());
  }

  @Test
  void go_225563() {
    Collection<String> results = objectUnderTest.process("225563");
    assertEquals(21, results.size());
    assertEquals(
        "[2(2)5(5)6(3), 22(5)563, (2)255(6)3, (2)25563, 22(5)56(3), (2)2556(3), 2(2)5563, 225(5)63, (2)25(5)63, 2(2)556(3), 22556(3), 2(2)5(5)63, (2)25(5)6(3), 22(5)5(6)3, (2)2(5)56(3), 225563, 225(5)6(3), (2)2(5)563, 2(2)55(6)3, 2255(6)3, (2)2(5)5(6)3]",
        results.toString());
  }

  @Test
  void go_2255763() {
    Collection<String> results = objectUnderTest.process("2255763");
    assertEquals(34, results.size());
    assertEquals(
        "[(2)2(5)5763, 22557(6)3, 2(2)557(6)3, (2)2557(6)3, 22(5)5763, 2(2)5(5)7(6)3, 2(2)5(5)76(3), 225576(3), (2)255(7)6(3), 22(5)5(7)6(3), 2255(7)63, 2255(7)6(3), 2255763, (2)2(5)5(7)63, (2)25(5)76(3), 2(2)5(5)763, (2)2(5)57(6)3, 2(2)5576(3), (2)255763, 22(5)576(3), 225(5)76(3), (2)255(7)63, 2(2)55(7)6(3), 22(5)57(6)3, (2)25(5)7(6)3, (2)2(5)5(7)6(3), 22(5)5(7)63, 225(5)763, 225(5)7(6)3, 2(2)55(7)63, (2)25576(3), (2)25(5)763, (2)2(5)576(3), 2(2)55763]",
        results.toString());
  }
}
