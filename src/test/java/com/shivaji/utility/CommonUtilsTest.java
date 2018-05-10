package com.shivaji.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** @author Shivaji */
class CommonUtilsTest {

  @Test
  void join() {
    assertEquals("ABC", CommonUtils.join("A", "B", "C"));
  }

  @Test
  void isNotEmpty() {
    assertTrue(CommonUtils.isNotEmpty.apply("Something"));
    assertFalse(CommonUtils.isNotEmpty.apply(null));
    assertFalse(CommonUtils.isNotEmpty.apply(""));
  }

  @Test
  void isEmpty() {
    assertTrue(CommonUtils.isEmpty.apply(""));
    assertTrue(CommonUtils.isEmpty.apply(null));
    assertFalse(CommonUtils.isEmpty.apply("Something"));
  }
}
