package de.fabian.kluempers.aoc_2020;

import io.vavr.collection.List;

import java.util.ArrayList;
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
    return executeProgram(createProgram(originalInput)).statusCode;
  }

  @Override
  public Object part2() {
    var programs = new ArrayList<List<String>>();
    for (int i = 0; i < originalInput.size(); i++) {
      var program = originalInput.toJavaList();
      program.set(i, flipInstruction(program.get(i)));
      programs.add(List.ofAll(program));
    }
    for (List<String> program : programs) {
      var result = executeProgram(createProgram(program));
      if (result instanceof Success) {
        return result.statusCode;
      }
    }
    return null;
  }

  private static String flipInstruction(String instruction) {
    return instruction.startsWith("nop")
        ? "jmp" + instruction.substring(3)
        : instruction.startsWith("jmp")
        ? "nop" + instruction.substring(3)
        : instruction;
  }

  private static ExecutableInstruction[] createProgram(List<String> input) {
    var program = new ExecutableInstruction[input.size()];
    var iter = input.iterator();
    for (int index = 0; index < input.size(); index++) {
      program[index] = parseToInstruction(iter.next());
    }
    return program;
  }

  private static ExecutionResult executeProgram(ExecutableInstruction[] program) {
    var executedInstructions = new HashSet<Integer>();
    var pc = new ProgramCounter();
    var acc = new Accumulator();
    while (true) {
      if (executedInstructions.contains(pc.value)) {
        return new InfiniteLoop(acc.value);
      }
      if (pc.value == program.length - 1) {
        return new Success(acc.value);
      }
      executedInstructions.add(pc.value);
      program[pc.value].execute(pc, acc);
    }
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

sealed class ExecutionResult {
  final int statusCode;

  ExecutionResult(int statusCode) {
    this.statusCode = statusCode;
  }
}

final class Success extends ExecutionResult {

  Success(int statusCode) {
    super(statusCode);
  }
}

final class InfiniteLoop extends ExecutionResult {

  InfiniteLoop(int statusCode) {
    super(statusCode);
  }
}
