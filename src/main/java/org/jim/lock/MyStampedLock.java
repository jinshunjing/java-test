package org.jim.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * 替代读写锁
 * 1. 技术问题：park线程被中断后会返回，然后自旋，导致CPU飙升
 *
 * @author JSJ
 */
public class MyStampedLock {

    private StampedLock lock;

    public void run() {
        //StampedLock
    }

    public void read() {
        // 获取乐观读锁
        long stamp = lock.tryOptimisticRead();

        // TODO 处理业务

        // 检查乐观锁是否被污染
        if (!lock.validate(stamp)) {
            // 升级成悲观锁
            stamp = lock.readLock();
            try {
                // TODO 处理业务
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    public void write() {
        long stamp = lock.writeLock();
        try {
            // TODO 处理业务
        } finally {
            lock.unlockWrite(stamp);
        }
    }

}
