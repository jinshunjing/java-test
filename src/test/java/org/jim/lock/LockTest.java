package org.jim.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class LockTest {

    @Test
    public void testCondition() throws Exception {
        ConditionWorker worker = new ConditionWorker();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    worker.writeByCondition(i);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    worker.readByCondition();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    @Test
    public void testDeadLock() throws Exception {
        LockWorker worker = new LockWorker();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                worker.writeByLock(10);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                worker.readByLock();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}
