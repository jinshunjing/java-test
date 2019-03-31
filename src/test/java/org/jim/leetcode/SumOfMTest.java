package org.jim.leetcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SumOfMTest {

    @Test
    public void test() {
        int[] v = {0};
        int sum = 10;
        //System.out.println(checkSum(v, sum));

        v = new int[]{10};
        //System.out.println(checkSum(v, sum));

        v = new int[]{4,6};
        //System.out.println(checkSum(v, sum));

        v = new int[]{5,1,2,1};
        System.out.println(checkSum(v, sum));
    }

    public int checkSum(int[] v, int sum) {
        int n = v.length;

        // f[i][s] 取i个数和为s
        // 阶段：数组的长度为k，问题的规模为k
        int[][] f = new int[n+1][sum+1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= sum; j++) {
                f[i][j] = 0;
            }
        }

        // 起点可以
        f[0][0] = 1;

        // 放在最前面，v[k]不可以重复选取
        for (int k = 0; k < n; k++) {
            // k+1个整数
            int a = v[k];

            // 包含v[k]
            // 避免状态转移的影响
            for (int i = k+1; i > 0; i--) {
                for (int s = 0; s <= sum; s++) {
                    if (s-a>=0 && f[i-1][s-a] == 1) {
                        f[i][s] = 1;
                        System.out.println(i + ", " + s);
                    }
                }
            }

            System.out.println();
        }

        int res = 0;
        for (int i = 0; i <= n; i++) {
            if (f[i][sum] == 1) {
                res = 1;
                System.out.println(i + ", " + sum);
                break;
            }
        }
        return res;
    }

}
