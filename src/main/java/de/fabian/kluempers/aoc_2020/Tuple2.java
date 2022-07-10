package de.fabian.kluempers.aoc_2020;

public record Tuple2<First, Second>(First first, Second second) {

  public static <First,Second> F<Second,Tuple2<First,Second>> $(First f) {
    return x -> new Tuple2<>(f, x);
  }
}
