package org.jim.queue;

import java.util.PriorityQueue;

public class MyPriorityHeap {

    private Integer[] values;
    private int tail;

    public MyPriorityHeap(int size) {
        values = new Integer[size];
        tail = -1;
    }

    public void insert(int v) {
        int idx = 0;
        while (values[idx] != null) {
            if (values[idx] < v) {
                // right
                idx = (idx << 1) + 2;
            } else {
                // left
                idx = (idx << 1) + 1;
            }
        }
        values[idx] = v;
        tail = Math.max(tail, idx);
    }

    public void print() {
        int l = 0;
        for (int i = 0; i <= tail; i++) {
            if (values[i] == null) {
                System.out.print("**  ");
            } else {
                System.out.print(values[i] + "  ");
            }
            if (i == l) {
                System.out.println();
                l = (l << 1) + 2;
            }
        }
        System.out.println();
    }

    public static int find(int[] values, int k) {
        int n = values.length;
        PriorityQueue<Integer> heap = new PriorityQueue<>(k);
        int i = 0;
        for (; i < k; i++) {
            heap.offer(values[i]);
        }

        for (; i < n; i++) {
            if (values[i] > heap.peek()) {
                heap.poll();
                heap.offer(values[i]);
            }
        }

        return heap.peek();
    }
}
