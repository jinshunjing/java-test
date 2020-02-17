package org.jim.leetcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DijkstraTest {

    @Test
    public void testMin1() {
        Integer[][] map = new Integer[3][3];
        map[1][0] = 7;
        map[1][2] = 2;
        map[2][0] = 4;

        int a = 1, b = 0;
        System.out.println(Dijkstra.min(map, a, b));
    }

    @Test
    public void testMin() {
        int[][] map = {
                {-1, -1, -1},
                {5, -1, 2},
                {4, -1, -1}
        };
        int a = 1, b = 0;
        int d = Dijkstra.min(map, a, b);
        System.out.println(d);
    }
}
