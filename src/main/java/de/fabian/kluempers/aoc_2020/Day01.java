package de.fabian.kluempers.aoc_2020;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.function.Predicate.*;
import static de.fabian.kluempers.aoc_2020.Functions.*;
public class Day01 extends Puzzle {
  public Day01(io.vavr.collection.List<String> originalInput) {
    super(originalInput);
  }

  private final List<Integer> input = originalInput.toJavaList().stream().map(Integer::parseInt).toList();

  @Override
  public Integer part1() {
    return solution(1);
  }

  @Override
  public Integer part2() {
    return solution(2);
  }

  private Integer solution(int numExpands) {
    Stream<List<Integer>> stream = input.stream()
        .map(List::of);

    for (int i = 0; i < numExpands; i++) {
      stream = stream.flatMap(expand.$(input));
    }

    return stream
        .map(filterSolution)
        .filter(Optional::isPresent)
        .findFirst()
        .flatMap(Function.identity())
        .orElseThrow();
  }

  private static final F<List<Integer>, F<List<Integer>, Stream<List<Integer>>>> expand = input -> list ->
      input.stream().map(elem -> {
        list.add(elem);
        return list;
      });

  private static final F<List<Integer>, Optional<Integer>> filterSolution = list ->
      list.stream()
          .reduce(Integer::sum)
          .filter(isEqual(2020))
          .flatMap(__ -> list.stream().reduce(Functions::mul));

}
