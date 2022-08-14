package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

import java.util.HashSet;
import java.util.regex.Pattern;

public class Day08 extends Puzzle {
  public Day08(List<String> originalInput) {
    super(originalInput);
  }

  private static final Instruction ACC = argument -> (pc, acc) -> {
    acc.value += argument;
    pc.value++;
  };

  private static final Instruction JMP = argument -> (pc, acc) -> pc.value += argument;

  private static final Instruction NOP = argument -> (pc, acc) -> pc.value++;

  private static final Pattern INSTRUCTION_PATTERN = Pattern
      .compile("(?<instruction>acc|jmp|nop) (?<argument>[+-]\\d+)");

  @Override
  public Object part1() {
    var program = new ExecutableInstruction[originalInput.size()];
    var iter = originalInput.iterator();
    for (int index = 0; index < originalInput.size(); index++) {
      program[index] = parseToInstruction(iter.next());
    }
    var executedInstructions = new HashSet<Integer>();
    var pc = new ProgramCounter();
    var acc = new Accumulator();
    while (!executedInstructions.contains(pc.value)) {
      executedInstructions.add(pc.value);
      program[pc.value].execute(pc, acc);
    }
    return acc.value;
  }

  @Override
  public Object part2() {
    return null;
  }

  private static ExecutableInstruction parseToInstruction(String input) {
    var matcher = INSTRUCTION_PATTERN.matcher(input);
    if (matcher.matches()) {
      var instruction = matcher.group("instruction");
      var argument = Integer.parseInt(matcher.group("argument"));
      return switch (instruction) {
        case "acc" -> ACC.create(argument);
        case "jmp" -> JMP.create(argument);
        case "nop" -> NOP.create(argument);
        default -> throw new IllegalStateException("regex broken");
      };
    } else throw new IllegalArgumentException("input does not conform to instruction pattern");
  }
}

interface Instruction {
  ExecutableInstruction create(int argument);
}

interface ExecutableInstruction {
  void execute(ProgramCounter pc, Accumulator acc);
}

class ProgramCounter {
  int value;
}

class Accumulator {
  int value;
}

