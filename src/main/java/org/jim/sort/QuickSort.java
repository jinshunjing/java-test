package org.jim.sort;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class QuickSort {

    public static void find(int[] values, int a) {
        int idx = find(values, a, 0, values.length - 1);
        System.out.println(idx);
    }

    public static int find(int[] values, int a, int l, int r) {
        if (l > r) {
            return -1;
        }

        int m = (l + r) / 2;

        // 排除m
        if (values[m] == a) {
            return m;
        }

        // left
        if (values[l] < values[m]) {
            // left
            if (values[l] <= a && a < values[m]) {
                return find(values, a, l, m - 1);
            }
            // right
            else {
                return find(values, a, m + 1, r);
            }
        }
        // right
        else {
            // right
            if (values[m] < a && a <= values[r]) {
                return find(values, a, m + 1, r);
            }
            // left
            else {
                return find(values, a, l, m - 1);
            }
        }
    }

    public static void quickSort(int[] values, int l, int r) {
        if (l >= r) {
            return;
        }

        // 选最后一个
        int p = values[r];

        int i = l, j = r - 1;
        while (i < j) {
            while (values[i] <= p && i < j) {
                i++;
            }

            while (values[j] >= p && i < j) {
                j--;
            }

            // 找到不满足的一对，swap
            if (i < j) {
                int tmp = values[i];
                values[i] = values[j];
                values[j] = tmp;
                i++;
                j--;
            }
        }

        // 此时i == j
        // 有可能前面的元素都小于p
        if (values[j] > p) {
            values[r] = values[j];
            values[j] = p;
        } else {
            j = r;
        }

        quickSort(values, l, j - 1);
        quickSort(values, j + 1 , r);
    }

    public static void sort(int[] values, int l, int r) {
        if (r - l <= 0) {
            return;
        }

        int v = values[r];

        int i = l, j = r - 1;
        while (i < j) {
            while (i < j && values[i] <= v) {
                i++;
            }
            while (j > i && values[j] >= v) {
                j--;
            }

            if (i == j) {
                break;
            }

            int t = values[i];
            values[i] = values[j];
            values[j] = t;
        }

        if (values[i] > v) {
            values[r] = values[i];
            values[i] = v;
        } else {
            values[r] = values[i+1];
            values[i+1] = v;
        }

        sort(values, l, i - 1);
        sort(values, i + 1, r);
    }

}
