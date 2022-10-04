package com.cus.algoritm.leetcode.easy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 문제
 * https://leetcode.com/problems/two-sum/
 */
public class TwoSum {

  static Stream<Arguments> providerTestCase() {
    return Stream.of(
        arguments(new int[] {2, 7, 11, 15}, 9, new int[] {0, 1}),
        arguments(new int[] {3, 2, 4}, 6, new int[] {1, 2}),
        arguments(new int[] {3, 3}, 6, new int[] {0, 1})
    );
  }

  @ParameterizedTest
  @MethodSource("providerTestCase")
  public void execute(int[] numbers, int target, int[] expected) {
    int[] result = null;

    for (int i = 0; i < numbers.length; i++) {
      for (int j = i + 1; j < numbers.length; j++) {
        if (numbers[i] + numbers[j] == target) {
          result = new int[] {i, j};
        }
      }
    }

    assertThat(result).containsExactly(expected);
  }

}
