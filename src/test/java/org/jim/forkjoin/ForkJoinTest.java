package org.jim.forkjoin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class ForkJoinTest {

    @Test
    public void test() {
        List<Integer> values = Arrays.asList(7, 8, 9);
        MyForkJoinTask task = new MyForkJoinTask(values);
        Integer result = task.compute();
        System.out.println(result);
    }
}
