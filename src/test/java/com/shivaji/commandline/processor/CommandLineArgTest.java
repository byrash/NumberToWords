package com.shivaji.commandline.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** @author Shivaji */
class CommandLineArgTest {

  public static final String CURRENT_CLASS_FILE_PATH =
      CommandLineArg.class.getProtectionDomain().getCodeSource().getLocation().getPath()
          + "com/shivaji/commandline/processor/CommandLineArg.class";

  @Test
  public void test_WithInvalidDictPathAndNoFiles() {
    String[] args = {"-d/Users/Shivaji/somethingThatDoesNotExists.txt"};
    CommandLineArg parser = new CommandLineArg(args);
    // Default Dict should be loaded
    assertTrue(
        parser.getDictionary().toString().contains("dictionary.txt"),
        "default dict should have been loaded");
    assertTrue(parser.getInputNumFiles().isEmpty(), "No Files supplied should be empty");
  }

  @Test
  public void test_WithNonExistingFiles() {
    String[] args = {
      "/Users/Shivaji/somethingThatDoesNotExists1.txt",
      "/Users/Shivaji/somethingThatDoesNotExists2.txt"
    };
    CommandLineArg parser = new CommandLineArg(args);
    // Default Dict should be loaded
    assertTrue(
        parser.getDictionary().toString().contains("dictionary.txt"),
        "default dict should have been loaded");
    // Still no files as we passed invalid files
    assertTrue(parser.getInputNumFiles().isEmpty(), "No Files supplied should be empty");
  }

  @Test
  public void test_WithExistingFiles() {
    String[] args = {CURRENT_CLASS_FILE_PATH};
    CommandLineArg parser = new CommandLineArg(args);
    // Default Dict should be loaded
    assertTrue(
        parser.getDictionary().toString().contains("dictionary.txt"),
        "default dict should have been loaded");
    assertEquals(1, parser.getInputNumFiles().size(), "One file should be found");
  }

  @Test
  public void test_WithDictPathAndNoFiles() {
    String[] args = {"-d" + CURRENT_CLASS_FILE_PATH};
    CommandLineArg parser = new CommandLineArg(args);
    // Default Dict should be loaded
    assertTrue(
        parser.getDictionary().toString().contains(CURRENT_CLASS_FILE_PATH),
        "Supplied dict should have been loaded");
    assertTrue(parser.getInputNumFiles().isEmpty(), "No Files supplied should be empty");
  }

  @Test
  public void test_WithDictPathAndWithFiles() {
    String[] args = {"-d" + CURRENT_CLASS_FILE_PATH, CURRENT_CLASS_FILE_PATH};
    CommandLineArg parser = new CommandLineArg(args);
    // Default Dict should be loaded
    assertTrue(
        parser.getDictionary().toString().contains(CURRENT_CLASS_FILE_PATH),
        "Supplied dict should have been loaded");
    assertEquals(1, parser.getInputNumFiles().size(), "One file should be found");
  }
}
