package com.shivaji.dict;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/** @author Shivaji */
class DictionaryFileProcessorTest {

  @Test
  void go_WithNoPath() {
    assertEquals(Optional.empty(), DictionaryFileProcessor.process(null));
  }

  @Test
  void go_WithDictPath() throws URISyntaxException {
    Path dictPath =
        Paths.get(DictionaryFileProcessorTest.class.getResource("/test-dictionary.txt").toURI());
    Optional<DictionaryVo> go = DictionaryFileProcessor.process(dictPath);
    assertTrue(go.isPresent());
    Map<String, Collection<String>> numberToWords = go.get().getNumberToWords();
    assertNotNull(numberToWords);
    assertEquals(3, numberToWords.size());
    assertNotNull(numberToWords.get("225563"));
    assertEquals(4, numberToWords.get("225563").size());
    assertEquals("[ballme, call-me, ball-me, callme]", numberToWords.get("225563").toString());
    assertNotNull(numberToWords.get("2255"));
    assertEquals(2, numberToWords.get("2255").size());
    assertEquals("[call, ball]", numberToWords.get("2255").toString());
    assertNotNull(numberToWords.get("63"));
    assertEquals(1, numberToWords.get("63").size());
    assertEquals("[me]", numberToWords.get("63").toString());
  }
}
