package com.cus.algoritm.leetcode.medium;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://leetcode.com/problems/add-two-numbers/
 */
public class AddTwoNumbers {

  @Test
  public void case1() {
    ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
    ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
    List<Integer> result = getResult(execute(l1, l2));

    assertThat(result).containsExactly(7, 0, 8);
  }

  @Test
  public void case2() {
    ListNode l1 = new ListNode(9, new ListNode(9, new ListNode(9,
        new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9)))))));

    ListNode l2 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))));

    List<Integer> result = getResult(execute(l1, l2));

    assertThat(result).containsExactly(8, 9, 9, 9, 0, 0, 0, 1);
  }

  @Test
  public void case3() {
    ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(9)));
    ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4, new ListNode(9))));

    List<Integer> result = getResult(execute(l1, l2));

    assertThat(result).containsExactly(7, 0, 4, 0, 1);
  }

  public ListNode execute(ListNode l1, ListNode l2) {
    ListNode node = new ListNode();
    ListNode result = node;
    int remainder = 0;

    while (l1 != null || l2 != null) {
      int a = l1 != null ? l1.val : 0;
      int b = l2 != null ? l2.val : 0;

      l1 = l1 != null ? l1.next : null;
      l2 = l2 != null ? l2.next : null;

      int sum = a + b + remainder;

      if (sum > 9) {
        remainder = 1;
      } else {
        remainder = 0;
      }

      node.next = new ListNode(sum % 10);
      node = node.next;

      if (l1 == null && l2 == null && remainder == 1) {
        node.next = new ListNode(1);
      }
    }

    return result.next;
  }

  public List<Integer> getResult(ListNode result) {
    List<Integer> arr = new ArrayList<>();

    while (true) {
      if (result == null) {
        break;
      }
      arr.add(result.val);
      result = result.next;
    }
    return arr;
  }

  static class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

}
