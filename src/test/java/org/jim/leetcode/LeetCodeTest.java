package org.jim.leetcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
public class LeetCodeTest {

    @Test
    public void test1() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] res = twoSum(nums, target);
        System.out.println(res[0] +","+ res[1]);
    }

    @Test
    public void test2() {
        int[] nums = {3,4,5,1,2};
        System.out.println(findMin(nums));

        nums = new int[]{4,5,6,7,0,1,2};
        System.out.println(findMin(nums));

        nums = new int[]{4,5,6};
        System.out.println(findMin(nums));

        nums = new int[]{4,5,6,7,0};
        System.out.println(findMin(nums));

        nums = new int[]{2,2,2,0,1};
        System.out.println(findMin(nums));
    }

    public boolean canJump(int[] nums) {
        int n = nums.length;
        boolean[] f = new boolean[n];
        for (int i = 0; i < n; i++) {
            f[i] = false;
        }
        f[n-1] = true;

        for (int k = n - 1; k > 0; k--) {
            if (!f[k]) {
                continue;
            }

            for (int i = 0; i < k; i++) {
                if (nums[i] >= (k - i)) {
                    f[i] = true;
                }
            }
            if (f[0]) {
                return true;
            }
        }

        return false;
    }

    public int findMin(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        if (nums[0] < nums[n-1]) {
            return nums[0];
        }

        int l = 0, r = n-1;
        while (l < r) {
            int m = (l + r) / 2;

            if (m == l) {
                if (nums[l] < nums[r]) {
                    return nums[l];
                } else {
                    return nums[r];
                }
            }

            if (nums[m] > nums[l]) {
                l = m;
            } else if (nums[m] < nums[l]) {
                r = m;
            } else {
                if (nums[m] > nums[r]) {
                    l = m;
                } else if (nums[m] < nums[r]) {
                    r = m;
                } else {
                    // 两边都有可能
                    r--;
                }
            }
        }

        return nums[l+1];
    }

    public int lengthOfLongestSubstring(String s) {
        return 0;
    }

    public int[] twoSum(int[] nums, int target) {
        int[] vals = Arrays.copyOf(nums, nums.length);
        Arrays.sort(vals);
        int a = 0;
        int b = 0;
        for (int v : vals) {
            a = v;
            b = target - a;
            int i = Arrays.binarySearch(vals, b);
            if (i >= 0) {
                b = vals[i];
                break;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == a) {
                a = i;
                break;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == b) {
                b = i;
                break;
            }
        }

        return new int[]{a, b};
    }
}
