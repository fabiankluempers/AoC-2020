package de.fabian.kluempers.aoc_2020;

import java.util.function.Function;

public interface F<T,R> extends Function<T,R> {
  default R $(T t) {
    return apply(t);
  }
}
