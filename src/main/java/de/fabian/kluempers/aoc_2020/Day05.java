package de.fabian.kluempers.aoc_2020;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;

import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.fabian.kluempers.aoc_2020.Functions.check;

public class Day05 extends Puzzle {
  private final List<Integer> seatIds;

  private static final Range SEAT_ID_RANGE = Range.from(0).to(127 * 8 + 7);
  private static final Pattern INPUT_PATTERN = Pattern.compile("([FB]{7})([LR]{3})");

  private static final Range ROW_RANGE = Range.from(0).until(128);

  private static final Range COL_RANGE = Range.from(0).until(8);

  private static Range lowerHalf(Range range) {
    return Range.from(range.lower()).to(range.lower() + ((range.upper() - range.lower())) / 2);
  }

  private static Range upperHalf(Range range) {
    return Range.from(range.lower() + ((range.upper() - range.lower())) / 2 + 1).to(range.upper());
  }

  private static int findRow(String input) {
    Range row = List.ofAll(input.toCharArray())
        .foldLeft(ROW_RANGE, ((range, character) -> character == 'F' ? lowerHalf(range) : upperHalf(range)));
    check(row.lower() == row.upper(), () -> "can't identify row");
    return row.lower();
  }

  public Day05(List<String> originalInput) {
    super(originalInput);
    seatIds = originalInput
        .map(INPUT_PATTERN::matcher)
        .filter(Matcher::matches)
        .map(x -> Tuple.of(x.group(1), x.group(2)))
        .map(x -> x.map(Day05::findRow, Day05::findCol))
        .map(Day05::toSeatId);
  }

  private static int toSeatId(Tuple2<Integer, Integer> seatInfo) {
    return seatInfo._1 * 8 + seatInfo._2;
  }


  @Override
  public Object part1() {
    return seatIds.max();
  }

  private static int findCol(String input) {
    Range row = List.ofAll(input.toCharArray())
        .foldLeft(COL_RANGE, ((range, character) -> character == 'L' ? lowerHalf(range) : upperHalf(range)));
    check(row.lower() == row.upper(), () -> "can't identify col");
    return row.lower();
  }

  @Override
  public Object part2() {
    return SEAT_ID_RANGE.intStream()
        .filter(x -> !seatIds.contains(x))
        .filter(x -> seatIds.contains(x + 1) && seatIds.contains(x - 1))
        .boxed()
        .toList();
  }
}
