package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

public class Day03 extends Puzzle {
  public Day03(List<String> originalInput) {
    super(originalInput);
  }

  List<char[]> forest = originalInput.map(String::toCharArray);

  static long traverse(List<char[]> list, TobogganConfig config, int index, long acc) {
    return list.isEmpty() ? acc : traverse(
        list.drop(config.yStep()),
        config,
        index + config.xStep(),
        acc + mapSymbol(wrappingGet(list.head(), index))
    );
  }

  private record TobogganConfig(int xStep, int yStep) {
  }

  @Override
  public Long part1() {
    return traverse(forest, new TobogganConfig(3, 1), 0, 0);
  }

  @Override
  public Long part2() {
    return List.of(
        traverse(forest, new TobogganConfig(1, 1), 0, 0),
        traverse(forest, new TobogganConfig(3, 1), 0, 0),
        traverse(forest, new TobogganConfig(5, 1), 0, 0),
        traverse(forest, new TobogganConfig(7, 1), 0, 0),
        traverse(forest, new TobogganConfig(1, 2), 0, 0)
    ).reduce(Math::multiplyExact);
  }

  private static int mapSymbol(char symbol) {
    return switch (symbol) {
      case '.' -> 0;
      case '#' -> 1;
      default -> throw new IllegalArgumentException("can't map " + symbol);
    };
  }

  private static char wrappingGet(char[] arr, int index) {
    return arr[index % arr.length];
  }
}
