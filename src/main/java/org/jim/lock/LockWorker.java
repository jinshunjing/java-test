package org.jim.lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockWorker {

    private ReentrantLock lock1;
    private ReentrantLock lock2;

    private int value;

    public LockWorker() {
        lock1 = new ReentrantLock();
        lock2 = new ReentrantLock();
        new ReentrantReadWriteLock();
    }

    public void write(int nv) {
        value = nv;
        try {
            Thread.sleep((long)(Math.random() * 2000));
        } catch (Exception e) {
        }
    }

    public int read() {
        try {
            Thread.sleep((long)(Math.random() * 2000));
        } catch (Exception e) {
        }
        return value;
    }

    public void writeByLock(int nv) {
        lock1.lock();
        System.out.println("Holds lock 1");
        write(nv);

        lock2.lock();
        System.out.println("Holds lock 2");
        int v = read();
        System.out.println(v);

        lock2.unlock();
        System.out.println("Released lock 2");
        lock1.unlock();
        System.out.println("Released lock 1");
    }

    public void readByLock() {
        lock2.lock();
        System.out.println("Holds lock 2");
        int v = read();
        System.out.println(v);

        lock1.lock();
        System.out.println("Holds lock 1");
        write(v+v);

        lock1.unlock();
        System.out.println("Released lock 1");
        lock2.unlock();
        System.out.println("Released lock 2");
    }
}
