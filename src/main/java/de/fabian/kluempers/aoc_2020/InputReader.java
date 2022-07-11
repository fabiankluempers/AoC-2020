package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class InputReader {
  public static List<String> read(String input) {
    try {
      return List.ofAll(Files.readAllLines(Paths.get(input)));
    } catch (IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
  }
}
