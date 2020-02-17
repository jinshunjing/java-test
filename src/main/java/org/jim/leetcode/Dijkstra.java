package org.jim.leetcode;

public class Dijkstra {

    public static Integer min(Integer[][] map, int a, int b) {
        int n = map.length;

        Boolean[] s = new Boolean[n];
        Integer[] d = new Integer[n];

        d[a] = 0;

        while (true) {
            int k = -1, mind = -1;
            for (int i = 0; i < n; i++) {
                if (s[i] != null) {
                    continue;
                }
                if (d[i] == null) {
                    continue;
                }

                if (k == -1 || mind > d[i]) {
                    k = i;
                    mind = d[i];
                }
            }
            if (k == -1) {
                break;
            }

            s[k] = true;

            for (int j = 0; j < n; j++) {
                if (s[j] != null) {
                    continue;
                }
                if (map[k][j] == null) {
                    continue;
                }

                int td = mind + map[k][j];
                if (d[j] == null || td < d[j]) {
                    d[j] = td;
                }
            }
        }

        return d[b];
    }

    public static int min(int[][] map, int a, int b) {
        int n = map.length;
        int[] d = new int[n];
        for (int i = 0; i < n; i++) {
            d[i] = -1;
        }

        boolean[] s = new boolean[n];
        for (int i = 0; i < n; i++) {
            s[i] = false;
        }

        d[a] = 0;

        while (true) {
            // 找到距离最近的点
            int min = -1, k = -1;
            for (int i = 0; i < n; i++) {
                // 未处理的点
                if (s[i]) {
                    continue;
                }
                if (d[i] == -1) {
                    continue;
                }
                if (min == -1 || min > d[i]) {
                    min = d[i];
                    k = i;
                }
            }
            if (k == -1) {
                break;
            }

            s[k] = true;

            // 通过该节点的最短路
            for (int j = 0; j < n; j++) {
                if (s[j]) {
                    continue;
                }

                if (map[k][j] == -1) {
                    continue;
                }

                int tmp = d[k] + map[k][j];
                if (d[j] == -1 || tmp < d[j]) {
                    d[j] = tmp;
                }
            }
        }

        return d[b];
    }
}
