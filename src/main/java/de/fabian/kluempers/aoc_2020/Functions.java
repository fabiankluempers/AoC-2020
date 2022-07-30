package de.fabian.kluempers.aoc_2020;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public record Functions() {
  public static int mul(int x, int y) { return x * y; }

  public static F<String, String> stringAppend(String suffix) {
    return prefix -> prefix + suffix;
  }

  public static Predicate<String> stringMatches(Pattern pattern) {
    return s -> pattern.matcher(s).matches();
  }

  public static <T> List<T> append(T elem, List<T> list) {
    List<T> newList = new ArrayList<>(list);
    newList.add(elem);
    return newList;
  }

  public static <T> F<T,F<List<T>, List<T>>> append() {
    return elem -> list -> append(elem, list);
  }
}
