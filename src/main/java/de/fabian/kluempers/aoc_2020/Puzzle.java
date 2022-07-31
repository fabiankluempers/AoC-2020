package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

abstract class Puzzle {
  public final List<String> originalInput;

  public Puzzle(List<String> originalInput) {
    this.originalInput = originalInput;
  }

  public abstract Object part1();
  public abstract Object part2();
}
