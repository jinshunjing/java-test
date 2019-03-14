package org.jim.lock;

import java.util.concurrent.CyclicBarrier;

public class MyCyclickBarrier {

    public void test() throws Exception {
        // 创建循环栅栏
        CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {

            }
        });

        // 准备穿越栅栏
        barrier.await();
    }
}
