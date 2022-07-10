package de.fabian.kluempers.aoc_2020;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class InputReader {
  public static List<String> read(String input) {
    try {
      return Files.readAllLines(Paths.get(input));
    } catch (IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
  }
}
