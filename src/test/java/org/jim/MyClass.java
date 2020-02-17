package org.jim;

import java.util.concurrent.locks.ReentrantLock;

public class MyClass {


    private static MyClass INSTANCE;

    private static ReentrantLock lock = new ReentrantLock();

    public static MyClass getInstance() {
        if (INSTANCE == null) {

//            synchronized (MyClass.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new MyClass();
//                }
//            }

            lock.lock();
            if (INSTANCE == null) {
                INSTANCE = new MyClass();
            }
            lock.unlock();

        }
        return INSTANCE;
    }

    private MyClass() {

    }

}
