package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

abstract class Puzzle<Part1, Part2> {
  public final List<String> originalInput;

  public Puzzle(List<String> originalInput) {
    this.originalInput = originalInput;
  }

  public abstract Part1 part1();
  public abstract Part2 part2();
}
