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
}
