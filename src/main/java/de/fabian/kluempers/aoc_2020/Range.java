package de.fabian.kluempers.aoc_2020;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public record Range(int lower, int upper) implements Iterable<Integer> {
  public static Predicate<Integer> inRange(Range range) {
    return x -> range.lower <= x && x <= range.upper;
  }

  public boolean contains(int value) {
    return inRange(this).test(value);
  }

  public static RangeBuilder from(int lower) {
    return new RangeBuilder(lower);
  }

  public IntStream intStream() {
    return IntStream.range(lower, upper + 1);
  }

  public int size() {
    return upper - lower;
  }

  public final static class RangeBuilder {
    private final int lower;

    private RangeBuilder(int lower) {
      this.lower = lower;
    }

    public Range to(int upper) {
      return new Range(lower, upper);
    }

    public Range until(int upper) {
      return new Range(lower, upper-1);
    }
  }

  @Override
  public Iterator<Integer> iterator() {
    return new Iterator<>() {
      private int currentIndex = lower;

      @Override
      public boolean hasNext() {
        return currentIndex <= upper;
      }

      @Override
      public Integer next() {
        return currentIndex++;
      }
    };
  }
}
