package com.cus.algoritm.leetcode.medium;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://leetcode.com/problems/longest-palindromic-substring/
 * */
public class LongestSubstringWithoutRepeatingCharacters {
  @Test
  public void case1(){
    String str = "babad";

    String actual = execute(str);

    assertThat(actual).isEqualTo("bab");
  }

  @Test
  public void case2(){
    String str = "cbbd";

    String actual = execute(str);

    assertThat(actual).isEqualTo("bb");
  }

  @Test
  public void case3(){
    String str = "aacabdkacaa";

    String actual = execute(str);

    assertThat(actual).isEqualTo("aca");
  }

  public String execute(String s) {
    return null;
  }
}
