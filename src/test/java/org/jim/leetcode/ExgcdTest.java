package org.jim.leetcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ExgcdTest {

    @Test
    public void testExgcd() {
        int a = 5, b = 2;
        a = 6; b = 3;
        a = 27; b = 6;
        int[] res = Exgcd.exgcd(a, b);
        System.out.println(res[0] + ", " + res[1] + ", " + res[2]);
    }

}
