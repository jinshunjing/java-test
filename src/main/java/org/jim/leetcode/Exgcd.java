package org.jim.leetcode;

public class Exgcd {

    public static int[] exgcd(int a, int b) {
        if (b == 0) {
            return new int[]{a, 1, 0};
        }

        int[] list = exgcd(b, a%b);
        int gcd = list[0];
        int x = list[2];
        int y = list[1] - a/b * list[2];

        return new int[]{gcd, x, y};
    }

}
