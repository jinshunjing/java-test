package org.jim.leetcode;

/**
 * 原理：
 * 1. next[i+1] = j 表示 后缀pattern[i-j+1, i] 匹配 前缀patter[0, j-1]
 * 2. text[k] 与 pattern[i+1] 不匹配，则可以移动到 pattern[j] 再尝试
 */
public class KMP {

    public static void getNext1(char[] p, int[] next) {
        int n = p.length;

        int i = 0, j = -1;
        next[i] = j;

        // i+1
        while (i < n - 1) {
            if (j == -1 || p[i] == p[j]) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
    }

    public static int match(char[] s, char[] p) {
        int n = p.length, m = s.length;
        int[] next = new int[n];
        getNext1(p, next);

        int i = 0, j = 0;
        while (i < m && j < n) {
            if (j == -1 || s[i] == p[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        if (j == n) {
            return i - n;
        } else {
            return -1;
        }
    }

    public static void getNext(char[] pattern, int[] next) {
        int i = 0, j = -1;
        int m = pattern.length;

        // -1表示不再移动模式
        next[i] = j;

        // 处理next[i+1]
        while (i < m-1) {
            // 如果可以匹配上一次的前缀
            // 或者边界条件
            if (j == -1 || pattern[i] == pattern[j]) {
                i++;
                j++;

                next[i] = j;
            }
            // 如果不可以匹配，回溯
            else {
                j = next[j];
            }
        }
    }

    public static int match(char[] text, char[] pattern, int[] next) {
        int i = 0, j = 0;
        int n = text.length, m = pattern.length;

        // 处理text[i]
        while(i < n && j < m) {
            // 如果匹配
            // 或者边界条件
            if (j == -1 || text[i] == pattern[j]) {
                i++;
                j++;
            }
            // 如果不匹配，移动
            else {
                j = next[j];
            }
        }

        // 注意已经++过了
        if (j == m) {
            return i-m;
        } else {
            return -1;
        }
    }
}
