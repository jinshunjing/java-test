package org.jim.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionWorker {

    private ReentrantLock lock1;
    private ReentrantLock lock2;
    private ReentrantLock lock3;

    private Condition condition1;
    private Condition condition2;

    private List<Integer> data;

    public ConditionWorker() {
        data = new ArrayList<>();

        lock1 = new ReentrantLock();
        lock2 = new ReentrantLock();
        lock3 = new ReentrantLock();

        condition1 = lock3.newCondition();
        condition2 = lock3.newCondition();
    }

    public void write(int nv) {
        data.add(nv);
        try {
            Thread.sleep((long)(Math.random() * 2000));
        } catch (Exception e) {
        }
    }

    public void read() {
        try {
            Thread.sleep((long)(Math.random() * 2000));
        } catch (Exception e) {
        }
        Integer v = data.remove(0);
        System.out.println(v);
    }

    public void writeByLock(int nv) {
        lock3.lock();
        write(nv);
        lock3.unlock();
    }

    public void readByLock() {
        lock3.lock();
        read();
        lock3.unlock();
    }

    public void writeByCondition(int nv) {
        lock3.lock();

        if (data.size() == 1) {
            System.out.println("Wait data to empty");
            try {
                condition1.await();
            } catch (Exception e) {
                lock3.unlock();
                return;
            }
        }

        write(nv);

        System.out.println("Signal data is full");
        condition2.signal();

        lock3.unlock();
    }

    public void readByCondition() {
        lock3.lock();

        if (data.isEmpty()) {
            System.out.println("Wait data to full");
            try {
                condition2.await();
            } catch (Exception e) {
                lock3.unlock();
                return;
            }
        }

        read();

        System.out.println("Signal data is empty");
        condition1.signal();

        lock3.unlock();
    }
}
