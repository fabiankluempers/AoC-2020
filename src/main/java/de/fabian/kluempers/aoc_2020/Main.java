package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

public class Main {
  public static void main(String[] args) {
    int day = 2;
    List<String> input = InputReader.read(String.format("input/Day%02d.txt", day));
    Puzzle<Integer, Integer> puzzle = new Day02(input);
    System.out.println(puzzle.part1());
    System.out.println(puzzle.part2());
  }
}