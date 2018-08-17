package com.shivaji.commandline.processor;

import static com.shivaji.utility.Constants.NOTHING;
import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Parses and holds the command lines supplied
 *
 * @author Shivaji Byrapaneni
 */
public class CommandLineArg implements AutoCloseable {

  private static final Logger LOG = Logger.getLogger(CommandLineArg.class.getName());
  public static final String DICT_CMD_ARG_INDICATOR = "-d";
  private FileSystem fs = null;

  // If not supplied we fallback on default; So, always will have value
  private final Path dictionary;
  private final Collection<Path> inputNumFiles;

  public CommandLineArg(String[] args) {
    List<String> argsList = Arrays.stream(args).collect(Collectors.toList());
    if (args.length > 0) {
      dictionary = getDictionary(argsList);
      argsList =
          argsList
              .stream()
              .filter(arg -> !arg.startsWith(DICT_CMD_ARG_INDICATOR))
              .collect(Collectors.toList());
      inputNumFiles = getNumberFiles(argsList);
    } else {
      dictionary = getDictionary(argsList);
      inputNumFiles = new ArrayList<>();
    }
  }

  private Collection<Path> getNumberFiles(List<String> argsList) {
    final Collection<Path> files = new ArrayList<>();
    argsList
        .stream()
        .forEach(
            arg -> {
              Path file = Paths.get(arg);
              if (file.toFile().exists()) {
                files.add(file);
              } else {
                LOG.warning(format("Ignoring inout file [{0}] as its not valid", arg));
              }
            });
    return files;
  }

  private Path getDictionary(List<String> argsList) {
    List<String> dictArg =
        argsList
            .stream()
            .filter(arg -> arg.startsWith(DICT_CMD_ARG_INDICATOR))
            .collect(Collectors.toList());
    if (!dictArg.isEmpty() && dictArg.size() == 1) { // Only one dict accepted
      String dictPath = dictArg.get(0).replace(DICT_CMD_ARG_INDICATOR, NOTHING);
      Path dict = Paths.get(dictPath);
      if (dict.toFile().exists()) {
        return dict;
      }
      LOG.warning(format("DictionaryVo file path supplied [{0}] is invalid", dictPath));
    }
    LOG.info("Falling back to system default dictionary");
    try {
      URL url = CommandLineArg.class.getResource("/dictionary.txt");
      return getDictFilePath(url);
    } catch (Exception e) {
      throw new IllegalStateException(
          "Catastrophic system exception. Unable to load default dict ", e);
    }
  }

  public Path getDictFilePath(URL url) throws IOException, URISyntaxException {
    if (url.toString().startsWith("jar:")) {
      final Map<String, String> env = new HashMap<>();
      final String[] array = url.toString().split("!");
      fs = FileSystems.newFileSystem(URI.create(array[0]), env);
      return fs.getPath(array[1]);
    } else {
      return Paths.get(url.toURI());
    }
  }

  public Path getDictionary() {
    return dictionary;
  }

  public Collection<Path> getInputNumFiles() {
    return inputNumFiles;
  }

  @Override
  public void close() {
    if (null != fs) {
      try {
        fs.close();
      } catch (IOException e) {
        LOG.severe(format("Unable to close file system [{0}]", e.getMessage()));
      }
    }
  }
}
