package org.jim.leetcode;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class PriorityQueueTest {


    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }


        k = n - k + 1;

        PriorityQueue<Integer> pq = new PriorityQueue<>(n);
        for (int i : nums) {
            pq.offer(i);
        }
        int v = nums[0];
        while (k > 0) {
            v = pq.poll();
            k--;
        }
        return v;
    }

    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        int n = intervals.size();
        int[] points = new int[n<<1];
        int k = 0;
        for (Interval i : intervals) {
            points[k++] = i.start;
            points[k++] = i.end;
        }



        int l = Arrays.binarySearch(points, newInterval.start);
        if (l < 0) {
            l = 0 - l - 1;
        }

        int r = Arrays.binarySearch(points, newInterval.end);
        if (r < 0) {
            r = 0 - r - 1;
        }

        List<Interval> res = new ArrayList<>();
        k = 0;
        int a, b;
        boolean f = false;
        while (k < n) {
            // 线段在前面
            a = k<<1;
            b = points[a+1];
            if (b > newInterval.start) {
                res.add(new Interval(points[a], b));
                k++;
                continue;
            }

            // 右边接上
        }

        if (!f) {
            res.add(newInterval);
        }

        return res;
    }


}
