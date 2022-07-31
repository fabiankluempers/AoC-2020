package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Predicate.*;

public class Day02 extends Puzzle {

  public Day02(List<String> originalInput) {
    super(originalInput);
  }
  private record Password(int lower, int upper, char character, String password) {
    private static final Pattern inputPattern = Pattern.compile("(\\d+)-(\\d+)\\s(\\w):\\s(\\w+)");

    boolean fulfillsP1Policy() {
      int count = List.ofAll(password.toCharArray()).count(isEqual(character));
      return lower <= count && count <= upper;
    }

    boolean fulfillsP2Policy() {
      return password.charAt(lower - 1) == character ^ password.charAt(upper - 1) == character;
    }

    static Password parse(String input) {
      Matcher matcher = inputPattern.matcher(input);
      if (matcher.find()) {
        return new Password(
            Integer.parseInt(matcher.group(1)),
            Integer.parseInt(matcher.group(2)),
            matcher.group(3).charAt(0),
            matcher.group(4)
        );
      } else throw new IllegalArgumentException();
    }
  }

  private final List<Password> passwords = originalInput.map(Password::parse);

  @Override
  public Integer part1() {
    return passwords.count(Password::fulfillsP1Policy);
  }

  @Override
  public Integer part2() {
    return passwords.count(Password::fulfillsP2Policy);
  }
}


