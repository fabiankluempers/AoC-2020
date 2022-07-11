package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;
public class Day04 extends Puzzle<Integer, Integer> {

  public Day04(List<String> originalInput) {
    super(originalInput);
  }

  static boolean validatePassport(String passport) {
    return List.of(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid"
    ).map(passport::contains).reduce(Boolean::logicalAnd);
  }

  static List<String> parseToIdentification(List<String> input, List<String> acc) {
    return input.isEmpty() ? acc : parseToIdentification(
        input.dropUntil(String::isBlank).tailOption().getOrElse(List.empty()),
        acc.prepend(input.takeUntil(String::isBlank).reduce(String::concat))
    );
  }

  @Override
  public Integer part1() {
    return parseToIdentification(originalInput, List.empty()).map(Day04::validatePassport).count(x -> x);
  }

  @Override
  public Integer part2() {
    return null;
  }
}
