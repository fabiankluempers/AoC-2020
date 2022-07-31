package de.fabian.kluempers.aoc_2020;

import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Try;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static de.fabian.kluempers.aoc_2020.Functions.*;
import static de.fabian.kluempers.aoc_2020.Range.from;

public class Day04 extends Puzzle {

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

  private static final Predicate<Map<String, String>> isValidPassport = and(
      validateField("byr", validateYear(from(1920).to(2002))),
      validateField("iyr", validateYear(from(2010).to(2020))),
      validateField("eyr", validateYear(from(2020).to(2030))),
      validateField("hgt", Day04::validateHeight),
      validateField("hcl", stringMatches(HAIR_COLOR_PATTERN)),
      validateField("ecl", VALID_EYE_COLORS::contains),
      validateField("pid", stringMatches(PASSPORT_ID_PATTERN))
  );

  private static boolean validatePassportKeys(String passport) {
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

  private static Predicate<Map<String, String>> validateField(String key, Predicate<String> validationFunc) {
    return map -> map.get(key).map(validationFunc::test).getOrElse(false);
  }

  private static Predicate<String> validateYear(Range range) {
    return year -> Try.of(() -> Integer.parseInt(year))
        .map(range::contains)
        .getOrElse(false);
  }

  private static boolean validateHeight(String height) {
    var matcher = HEIGHT_PATTERN.matcher(height);
    if (matcher.matches()) {
      int value = Integer.parseInt(matcher.group(1));
      String unit = matcher.group(2);
      return switch (unit) {
        case "cm" -> VALID_CM_RANGE.contains(value);
        case "in" -> VALID_INCH_RANGE.contains(value);
        default -> false;
      };
    }
    return false;
  }

  private static Map<String, String> toKeyValue(String input) {
    return HashMap.ofAll(
        KEY_VALUE_PATTERN.matcher(input).results(),
        matchResult -> Tuple.of(matchResult.group(1), matchResult.group(2))
    );
  }

  private static String toIdentification(List<String> input) {
    return input.map(stringAppend(" ")).reduce(String::concat);
  }

  @Override
  public Integer part1() {
    return chunkedByBlankLines(originalInput)
        .map(Day04::toIdentification)
        .filter(Day04::validatePassportKeys)
        .size();
  }


  @Override
  public Integer part2() {
    return chunkedByBlankLines(originalInput)
        .map(Day04::toIdentification)
        .map(Day04::toKeyValue)
        .filter(isValidPassport)
        .size();
  }
}
