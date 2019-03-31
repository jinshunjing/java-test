package org.jim.leetcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class KMPTest {

    @Test
    public void testNext() {
        char[] pattern = {'a', 'a', 'a'};
        pattern = new char[]{'a', 'b', 'a', 'b', 'c'};

        int[] next = new int[pattern.length];

        KMP.getNext(pattern, next);

        for (int i = 0; i < next.length; i++) {
            System.out.print(" " + next[i]);
        }
        System.out.println();
    }

    @Test
    public void testMatch() {
        char[] text = new char[]{'a', 'b', 'a', 'b', 'a', 'b', 'd'};
        char[] pattern = new char[]{'a', 'b', 'a', 'b', 'c'};

        int[] next = new int[pattern.length];
        KMP.getNext(pattern, next);

        int idx = KMP.match(text, pattern, next);
        System.out.println(idx);
    }

    @Test
    public void testMatch2() {
        String text = "abababc";
        String pattern = "ababc";
        int idx = match(text, pattern);
        System.out.println(idx);
    }

    public int match(String text, String pattern) {
        int n = pattern.length();
        int[] next = new int[n];
        getNext(pattern, next);

        int m = text.length();

        int i = 0, j = 0;
        while (i < m && j < n) {
            if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        if (j == n) {
            return i-n;
        }
        return -1;
    }

    public void getNext(String pattern, int[] next) {
        int n = pattern.length();
        int i = 0, j = -1;

        next[i] = j;

        while (i < n-1) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
    }

}

