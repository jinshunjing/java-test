package org.jim;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
public class MyTest {

    @Test
    public void test1() {
        int[] values = {3, 4, 1, 1, 3, 18, 30, 20, 21};
        List<List<Integer>> res = sum(values, 21);
        for (List<Integer> p : res) {
            System.out.println(p.get(0) + ","  + p.get(1));
        }
    }

    public List<List<Integer>> sum(int[] values, int s) {
        List<List<Integer>> res = new ArrayList<>();

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            int v = values[i];
            List<Integer> list = map.get(v);
            if (null == list) {
                list = new ArrayList<>();
                map.put(v, list);
            }
            list.add(i);
        }

        for (int i = 0; i < values.length; i++) {
            int t = s - values[i];
            List<Integer> list = map.get(t);
            if (list == null) {
                continue;
            }

            for (int j : list) {
                if (j != i) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }

        return res;
    }

}
