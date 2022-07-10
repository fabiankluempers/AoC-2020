package de.fabian.kluempers.aoc_2020;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    int day = 1;
    List<String> input = InputReader.read(String.format("input/Day%02d.txt", day));
    Puzzle<Integer, Integer> puzzle = new Day01(input);
    System.out.println(puzzle.part1());
    System.out.println(puzzle.part2());
  }
}