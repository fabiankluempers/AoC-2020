package de.fabian.kluempers.aoc_2020;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Set;

import java.util.function.Predicate;

public class Day09 extends Puzzle {
  public Day09(List<String> originalInput) {
    super(originalInput);
  }

  @Override
  public Object part1() {
    return originalInput
        .map(Long::parseLong)
        .sliding(26)
        .map(window -> window.splitAt(25))
        .filterNot(tuple -> isValidAddResult(tuple._1.toSet(), tuple._2.head()))
        .map(Tuple2::_2)
        .map(List::head)
        .head();
  }

  private static Predicate<Long> isValidMulResult(Set<Long> preamble) {
    return number -> {
      for (Long candidate : preamble) {
        var divisionResult = number / candidate;
        if (number % candidate == 0 &&
            divisionResult != candidate &&
            preamble.contains(divisionResult)) {
          return true;
        }
      }
      return false;
    };
  }

  private static boolean isValidAddResult(Set<Long> preamble, Long number) {
    for (Long candidate : preamble) {
      var subtractionResult = number - candidate;
      if (number != subtractionResult && preamble.contains(subtractionResult)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Object part2() {
    var part1 = part1();
    for (int i = 2; i <= originalInput.size(); i++) {
      var result = originalInput.map(Long::parseLong)
          .sliding(i)
          .filter(window -> window.sum().equals(part1))
          .headOption()
          .map(window -> window.min().get() + window.max().get());
      if (result.isDefined()) return result.get();
    }
    throw new IllegalStateException("no solution");
  }
}
