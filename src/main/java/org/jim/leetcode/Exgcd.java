package org.jim.leetcode;

/**
 * 扩展欧几里得
 */
public class Exgcd {

    public static int[] egcd(int a, int b) {
        // 结束
        if (b == 0) {
            return new int[]{a, 1, 0};
        }
        // 递归
        int[] val = egcd(b, a%b);
        return new int[]{val[0], val[2], val[1] - a/b * val[2]};
    }

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
