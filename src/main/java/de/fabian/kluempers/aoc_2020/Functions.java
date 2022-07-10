package de.fabian.kluempers.aoc_2020;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public record Functions() {
  public static int mul(int x, int y) { return x * y; }

  public static <T> List<T> append(T elem, List<T> list) {
    List<T> newList = new ArrayList<>(list);
    newList.add(elem);
    return newList;
  }

  public static <T> F<T,F<List<T>, List<T>>> append() {
    return elem -> list -> append(elem, list);
  }
}
