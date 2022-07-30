package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Try;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static de.fabian.kluempers.aoc_2020.Functions.*;
import static de.fabian.kluempers.aoc_2020.Range.*;

public class Day04 extends Puzzle<Integer, Integer> {

  public Day04(List<String> originalInput) {
    super(originalInput);
  }

  private static final Pattern KEY_VALUE_PATTERN = Pattern.compile("([a-z]{3}):(\\S+)");
  private static final Pattern HEIGHT_PATTERN = Pattern.compile("(\\d+)(cm|in)");
  private static final Pattern HAIR_COLOR_PATTERN = Pattern.compile("#[\\da-f]{6}");
  private static final Pattern PASSPORT_ID_PATTERN = Pattern.compile("\\d{9}");
  private static final Range VALID_CM_RANGE = from(150).to(193);
  private static final Range VALID_INCH_RANGE = from(59).to(76);
  private static final Set<String> VALID_EYE_COLORS = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

  static boolean validatePassportKeys(String passport) {
    return List.of(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid"
    ).map(passport::contains).reduce(Boolean::logicalAnd);
  }

  static boolean validatePassport(Map<String, String> passport) {
    return List.of(
        validateField(passport, "byr", validateYear(1920, 2002)),
        validateField(passport, "iyr", validateYear(2010, 2020)),
        validateField(passport, "eyr", validateYear(2020, 2030)),
        validateField(passport, "hgt", Day04::validateHeight),
        validateField(passport, "hcl", stringMatches(HAIR_COLOR_PATTERN)),
        validateField(passport, "ecl", VALID_EYE_COLORS::contains),
        validateField(passport, "pid", stringMatches(PASSPORT_ID_PATTERN))
    ).reduce(Boolean::logicalAnd);
  }

  static boolean validateField(Map<String, String> map, String key, Predicate<String> validationFunc) {
    return map.get(key).map(validationFunc::test).getOrElse(false);
  }

  static Predicate<String> validateYear(int lower, int upper) {
    return year -> Try.of(() -> Integer.parseInt(year))
        .map(inRange(from(lower).to(upper))::test)
        .getOrElse(false);
  }

  static boolean validateHeight(String height) {
    var matcher = HEIGHT_PATTERN.matcher(height);
    if (matcher.find()) {
      int value = Integer.parseInt(matcher.group(1));
      String unit = matcher.group(2);
      return switch (unit) {
        case "cm" -> inRange(VALID_CM_RANGE).test(value);
        case "in" -> inRange(VALID_INCH_RANGE).test(value);
        default -> false;
      };
    }
    return false;
  }

  static Map<String, String> toKeyValue(String input) {
    var matcher = KEY_VALUE_PATTERN.matcher(input);
    var map = HashMap.<String, String>empty();
    while (matcher.find()) {
      var key = matcher.group(1);
      var value = matcher.group(2);
      map = map.put(key, value);
    }
    return map;
  }

  static List<String> parseToIdentification(List<String> input, List<String> acc) {
    return input.isEmpty() ? acc : parseToIdentification(
        input.dropUntil(String::isBlank).tailOption().getOrElse(List.empty()),
        acc.prepend(input.takeUntil(String::isBlank).map(stringAppend(" ")).reduce(String::concat))
    );
  }

  @Override
  public Integer part1() {
    return parseToIdentification(originalInput, List.empty())
        .map(Day04::validatePassportKeys)
        .count(x -> x);
  }


  @Override
  public Integer part2() {
    return parseToIdentification(originalInput, List.empty())
        .map(Day04::toKeyValue)
        .map(Day04::validatePassport)
        .count(x -> x);
  }
}
