package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static de.fabian.kluempers.aoc_2020.Functions.chunkedByBlankLines;

public class Day06 extends Puzzle{
  public Day06(List<String> originalInput) {
    super(originalInput);
  }

  private static Function<List<String>, Integer> sizeOfAnswerSet(BinaryOperator<HashSet<Character>> operation) {
    return answers -> answers
        .map(String::toCharArray)
        .map(HashSet::ofAll)
        .reduce(operation)
        .size();
  }
  @Override
  public Object part1() {
    return chunkedByBlankLines(originalInput)
        .map(sizeOfAnswerSet(HashSet::addAll))
        .sum();
  }

  @Override
  public Object part2() {
    return chunkedByBlankLines(originalInput)
        .map(sizeOfAnswerSet(HashSet::intersect))
        .sum();
  }
}
