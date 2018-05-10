package com.shivaji.word.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PatternNumberToPatternWordsGeneratorTest {

  static final Map<String, Set<String>> replacements = new HashMap<>(3);
  public static final Function<String, Boolean> isEmpty = str -> str == null || str.isEmpty();

  @BeforeAll
  protected static void init() {
    Set<String> s1 = new HashSet<>(2);
    s1.add("W1");
    s1.add("W2");
    Set<String> s2 = new HashSet<>(2);
    s2.add("W3");
    s2.add("W4");
    Set<String> s3 = new HashSet<>(2);
    s3.add("W5");
    s3.add("W6");
    replacements.put("1", s1);
    replacements.put("2", s1);
    replacements.put("3", s2);
    replacements.put("4", s2);
    replacements.put("5", s3);
    replacements.put("6", s3);
  }

  @Test
  void testCase1() {
    Collection<String> result = new HashSet<>();
    String pattern = "(1)2(3)4(5)6";
    List<String> nosToReplace =
        Arrays.stream(pattern.split("\\(.*?\\)"))
            .filter(item -> !isEmpty.apply(item))
            .collect(Collectors.toList());
    PatternNumberToPatternWordsGenerator.go(pattern, nosToReplace, replacements, result);
    assertEquals(
        "[(1)W1(3)W3(5)W6, (1)W1(3)W3(5)W5, (1)W1(3)W4(5)W5, (1)W1(3)W4(5)W6, (1)W2(3)W3(5)W5, (1)W2(3)W3(5)W6, (1)W2(3)W4(5)W5, (1)W2(3)W4(5)W6]",
        result.toString());
  }

  @Test
  void testCase2() {
    Collection<String> result = new HashSet<>();
    String pattern = "(1)23456";
    List<String> nosToReplace =
        Arrays.stream(pattern.split("\\(.*?\\)"))
            .filter(item -> !isEmpty.apply(item))
            .collect(Collectors.toList());
    PatternNumberToPatternWordsGenerator.go(pattern, nosToReplace, replacements, result);
    assertEquals("[]", result.toString());
  }

  @Test
  void testCase3() {
    Collection<String> result = new HashSet<>();
    String pattern = "(1)2(3)4(5)";
    List<String> nosToReplace =
        Arrays.stream(pattern.split("\\(.*?\\)"))
            .filter(item -> !isEmpty.apply(item))
            .collect(Collectors.toList());
    PatternNumberToPatternWordsGenerator.go(pattern, nosToReplace, replacements, result);
    assertEquals("[(1)W1(3)W3(5), (1)W1(3)W4(5), (1)W2(3)W3(5), (1)W2(3)W4(5)]", result.toString());
  }

  @Test
  void testCase4() {
    Collection<String> result = new HashSet<>();
    String pattern = "1(2)3(4)5";
    List<String> nosToReplace =
        Arrays.stream(pattern.split("\\(.*?\\)"))
            .filter(item -> !isEmpty.apply(item))
            .collect(Collectors.toList());
    PatternNumberToPatternWordsGenerator.go(pattern, nosToReplace, replacements, result);
    assertEquals(
        "[W1(2)W3(4)W5, W1(2)W3(4)W6, W1(2)W4(4)W6, W2(2)W3(4)W5, W1(2)W4(4)W5, W2(2)W4(4)W6, W2(2)W4(4)W5, W2(2)W3(4)W6]",
        result.toString());
  }
}
