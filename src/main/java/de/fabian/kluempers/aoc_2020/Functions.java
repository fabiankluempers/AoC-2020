package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public record Functions() {
  public static int mul(int x, int y) { return x * y; }

  public static F<String, String> stringAppend(String suffix) {
    return prefix -> prefix + suffix;
  }

  public static F<String, Boolean> stringContains(String ref) {
    return string -> string.contains(ref);
  }

  public static void check(boolean condition, Supplier<String> lazyMessage) {
    if (!condition) throw new IllegalStateException(lazyMessage.get());
  }

  @SafeVarargs
  public static <T> Predicate<T> and(Predicate<T> identity, Predicate<T>... predicates) {
    return Arrays.stream(predicates).reduce(identity, Predicate::and);
  }

  @SafeVarargs
  public static <T> Predicate<T> or(Predicate<T> identity, Predicate<T>... predicates) {
    return Arrays.stream(predicates).reduce(identity, Predicate::or);
  }

  public static Predicate<String> stringMatches(Pattern pattern) {
    return s -> pattern.matcher(s).matches();
  }


  public static List<List<String>> chunkedByBlankLines(List<String> input) {
    return chunkedByBlankLines(input, List.empty());
  }
  private static List<List<String>> chunkedByBlankLines(List<String> input, List<List<String>> acc) {
    return input.isEmpty() ? acc : chunkedByBlankLines(
        input.dropUntil(String::isBlank).tailOption().getOrElse(List.empty()),
        acc.prepend(input.takeUntil(String::isBlank))
    );
  }
}
