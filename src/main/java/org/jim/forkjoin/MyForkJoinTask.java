package org.jim.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask有返回值，RecursiveAction没有返回值
 *
 */
public class MyForkJoinTask extends RecursiveTask<Integer> {

    private List<Integer> values;

    public MyForkJoinTask(List<Integer> values) {
        this.values = new ArrayList<>(values);
    }

    /**
     * 关键方法
     *
     * @return
     */
    public Integer compute() {
        // 结束条件
        if (values.size() == 1) {
            return values.get(0);
        }

        // 分成多个子问题
        int split = values.size() >>> 1;
        MyForkJoinTask task1 = new MyForkJoinTask(values.subList(0, split));
        MyForkJoinTask task2 = new MyForkJoinTask(values.subList(split, values.size()));

        // 执行子问题
        invokeAll(task1, task2);

        // 合并子问题的结果
        return task1.join() + task2.join();
    }

}
