package com.shivaji.commandline.processor;

import static com.shivaji.utility.Constants.NOTHING;
import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Parses and holds the command lines supplied
 *
 * @author Shivaji Byrapaneni
 */
public class CommandLineArgsParser {

  private static final Logger LOG = Logger.getLogger(CommandLineArgsParser.class.getName());
  public static final String DICT_CMD_ARG_INDICATOR = "-d";
  private FileSystem fs = null;

  private final Optional<Path> dictionary;
  private final Collection<Path> numberFiles;

  public CommandLineArgsParser(String[] args) {
    List<String> argsList = Arrays.stream(args).collect(Collectors.toList());
    if (args.length > 0) {
      dictionary = getDictionary(argsList);
      argsList =
          argsList
              .stream()
              .filter(arg -> !arg.startsWith(DICT_CMD_ARG_INDICATOR))
              .collect(Collectors.toList());
      numberFiles = getNumberFiles(argsList);
    } else {
      dictionary = getDictionary(argsList);
      numberFiles = new ArrayList<>();
    }
  }

  private Collection<Path> getNumberFiles(List<String> argsList) {
    final Collection<Path> files = new ArrayList<>();
    argsList
        .stream()
        .forEach(
            arg -> {
              Path file = Paths.get(arg);
              if (Files.isRegularFile(file)) {
                files.add(file);
              } else {
                LOG.warning(format("Ignoring inout file [{0}] as its not valid", arg));
              }
            });
    return files;
  }

  private Optional<Path> getDictionary(List<String> argsList) {
    List<String> dictArg =
        argsList
            .stream()
            .filter(arg -> arg.startsWith(DICT_CMD_ARG_INDICATOR))
            .collect(Collectors.toList());
    if (!dictArg.isEmpty() && dictArg.size() == 1) { // Only one dict accepted
      String dictPath = dictArg.get(0).replace(DICT_CMD_ARG_INDICATOR, NOTHING);
      Path dict = Paths.get(dictPath);
      if (Files.isRegularFile(dict)) {
        return Optional.of(dict);
      }
      LOG.warning(format("DictionaryVo file path supplied [{0}] is invalid", dictPath));
    }
    LOG.info("Falling back to system default dictionary");
    try {
      URL url = CommandLineArgsParser.class.getResource("/dictionary.txt");
      return Optional.of(getDictFilePath(url));
    } catch (Exception e) {
      e.printStackTrace();
      LOG.warning(
          format(
              "Catastrophic system exception. Unable to load default dict [{0}]", e.getMessage()));
    }
    return Optional.empty();
  }

  public Path getDictFilePath(URL url) throws IOException, URISyntaxException {
    if (url.toString().startsWith("jar:")) {
      final Map<String, String> env = new HashMap<>();
      final String[] array = url.toString().split("!");
      fs = FileSystems.newFileSystem(URI.create(array[0]), env);
      final Path path = fs.getPath(array[1]);
      return path;
    } else {
      return Paths.get(url.toURI());
    }
  }

  public void cleanUp() {
    if (null != fs) {
      try {
        fs.close();
      } catch (IOException e) {
        LOG.severe(format("Unable to close file system [{0}]", e.getMessage()));
      }
    }
  }

  public Optional<Path> getDictionary() {
    return dictionary;
  }

  public Collection<Path> getNumberFiles() {
    return numberFiles;
  }
}
