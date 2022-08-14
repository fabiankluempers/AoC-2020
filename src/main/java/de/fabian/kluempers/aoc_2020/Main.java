package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

public class Main {
  public static void main(String[] args) {
    int day = 8;
    List<String> input = InputReader.read(String.format("input/Day%02d.txt", day));
    Puzzle puzzle = new Day08(input);
    System.out.println(puzzle.part1());
    System.out.println(puzzle.part2());
  }
}