//package org.jim.aqs;
//
//import sun.misc.Unsafe;
//
//import java.util.Collection;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.LockSupport;
//
///**
// * 对外开发的方法
// * acquire -
// * release -
// * acquireShared -
// * acquireSharedInterruptibly -
// * tryAcquireSharedNanos -
// * releaseShared -
// *
// * 子类要实现的方法
// * tryAcquire -
// * tryRelease -
// * tryAcquireShared -
// *
// * 获取资源的方法
// * acquireQueued -
// * doAcquireShared -
// *
// */
//public class AbstractQueuedSynchronizer {
//    /**
//     * 同步状态
//     */
//    private volatile int state;
//    /**
//     * 返回同步状态
//     */
//    protected final int getState() {
//        return state;
//    }
//    /**
//     * 更新同步状态
//     */
//    protected final void setState(int newState) {
//        state = newState;
//    }
//    /**
//     * 更新同步状态的原子操作CAS
//     */
//    protected final boolean compareAndSetState(int expect, int update) {
//        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
//    }
//
//    /**
//     * 同步队列的队首
//     * 队首是已经获取到资源的节点，它的等待状态不可能是cancelled。
//     * 它在释放的时候会唤醒后继节点，后继节点在获得到资源之后会把自己设置为队首。
//     */
//    private transient volatile Node head;
//    /**
//     * 同步队列的队尾
//     */
//    private transient volatile Node tail;
//
//
//    // 队列操作
//    // Queuing utilities
//
//    /**
//     * 为当前线程创建一个节点，并且插入队尾
//     *
//     * @param mode Node.EXCLUSIVE for exclusive, Node.SHARED for shared
//     */
//    private Node addWaiter(Node mode) {
//        // 创建节点
//        Node node = new Node(Thread.currentThread(), mode);
//
//        // 如果队尾存在，则更新队尾为当前节点
//        Node pred = tail;
//        if (pred != null) {
//            node.prev = pred;
//            if (compareAndSetTail(pred, node)) {
//                pred.next = node;
//                return node;
//            }
//        }
//
//        // 把当前节点加入队尾
//        enq(node);
//        return node;
//    }
//
//    /**
//     * 新节点插入队尾，返回新节点的前驱节点
//     */
//    private Node enq(final Node node) {
//        // 自旋
//        for (;;) {
//            Node t = tail;
//            // 如果队列为空，
//            if (t == null) {
//                // 新建一个空的队首节点
//                // WHY: 表示已经获得锁资源的线程，但是不直到具体是哪个线程
//                if (compareAndSetHead(new Node())) {
//                    // 设置队尾
//                    tail = head;
//                }
//            } else {
//                // 更新队尾为当前节点
//                node.prev = t;
//                if (compareAndSetTail(t, node)) {
//                    t.next = node;
//                    return t;
//                }
//            }
//        }
//    }
//
//    /**
//     * 重置队首
//     * 该操作不需要CAS，因为只有获取到锁的线程才能设置队首
//     */
//    private void setHead(Node node) {
//        head = node;
//        node.thread = null;
//        node.prev = null;
//    }
//
//    /**
//     * 重置队首，如果后继节点是共享模式，则释放队首。
//     * Sets head of queue, and checks if successor may be waiting
//     * in shared mode, if so propagating if either propagate > 0 or
//     * PROPAGATE status was set.
//     */
//    private void setHeadAndPropagate(Node node, int propagate) {
//        // 缓存原来的队首
//        Node h = head;
//        // 重置队首
//        setHead(node);
//
//        /*
//         * Try to signal next queued node if:
//         *   Propagation was indicated by caller,
//         *     or was recorded (as h.waitStatus either before
//         *     or after setHead) by a previous operation
//         *     (note: this uses sign-check of waitStatus because
//         *      PROPAGATE status may transition to SIGNAL.)
//         * and
//         *   The next node is waiting in shared mode,
//         *     or we don't know, because it appears null
//         *
//         * The conservatism in both of these checks may cause
//         * unnecessary wake-ups, but only when there are multiple
//         * racing acquires/releases, so most need signals now or soon
//         * anyway.
//         */
//        if (propagate > 0 || h == null || h.waitStatus < 0 ||
//                (h = head) == null || h.waitStatus < 0) {
//            // 如果后继节点是共享模式，则释放队首
//            Node s = node.next;
//            if (s == null || s.isShared()) {
//                doReleaseShared();
//            }
//        }
//    }
//
//    /**
//     * 释放同步队列的队首，共享模式。
//     * 队首节点的等待状态设置为0，唤醒后继节点，然后把等待状态设置为propagate，
//     * 直到队首节点变换之后才退出循环。
//     */
//    private void doReleaseShared() {
//        /*
//         * Ensure that a release propagates, even if there are other
//         * in-progress acquires/releases.  This proceeds in the usual
//         * way of trying to unparkSuccessor of head if it needs
//         * signal. But if it does not, status is set to PROPAGATE to
//         * ensure that upon release, propagation continues.
//         * Additionally, we must loop in case a new node is added
//         * while we are doing this. Also, unlike other uses of
//         * unparkSuccessor, we need to know if CAS to reset status
//         * fails, if so rechecking.
//         */
//        for (;;) {
//            Node h = head;
//            if (h != null && h != tail) {
//                int ws = h.waitStatus;
//                if (ws == Node.SIGNAL) {
//                    // 队首节点的等待状态是signal, 设置为0
//                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0)) {
//                        // loop to recheck cases
//                        continue;
//                    }
//                    // 唤醒后继
//                    unparkSuccessor(h);
//                }
//                // 队首节点的等待状态是0, 设置为传播
//                else if (ws == 0 &&
//                        !compareAndSetWaitStatus(h, 0, Node.PROPAGATE)) {
//                    // loop on failed CAS
//                    continue;
//                }
//            }
//            // loop if head changed
//            if (h == head) {
//                break;
//            }
//        }
//    }
//
//    /**
//     * 唤醒当前节点的后继节点。当前节点已经获得锁。
//     * 如果不存在或者已取消，则唤醒所有后继中第一个未被取消的后继节点。
//     */
//    private void unparkSuccessor(Node node) {
//        /*
//         * 当前节点的等待状态设置成0
//         * If status is negative (i.e., possibly needing signal) try
//         * to clear in anticipation of signalling.  It is OK if this
//         * fails or if status is changed by waiting thread.
//         */
//        int ws = node.waitStatus;
//        if (ws < 0) {
//            compareAndSetWaitStatus(node, ws, 0);
//        }
//
//        Node s = node.next;
//        if (s == null || s.waitStatus > 0) {
//            // 如果后继节点不存在或者已取消
//            // 找到所有后继中第一个未取消的节点
//            s = null;
//            for (Node t = tail; t != null && t != node; t = t.prev) {
//                if (t.waitStatus <= 0) {
//                    s = t;
//                }
//            }
//        }
//
//        // 唤醒线程
//        if (s != null) {
//            LockSupport.unpark(s.thread);
//        }
//    }
//
//    // Utilities for various versions of acquire
//
//    /**
//     * 每次获取资源失败之后，检查是否需要阻塞当前线程。
//     * 1. 只有前序节点的等待状态是signal才可以阻塞当前线程
//     * 2. 跳过所有的cancelled前序节点
//     * 3. 把前序节点的等待状态设置成signal
//     * Checks and updates status for a node that failed to acquire.
//     * Returns true if thread should block. This is the main signal
//     * control in all acquire loops.  Requires that pred == node.prev.
//     */
//    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
//        // 前序节点的等待状态是signal，可以阻塞
//        int ws = pred.waitStatus;
//        if (ws == Node.SIGNAL) {
//            /*
//             * This node has already set status asking a release
//             * to signal it, so it can safely park.
//             */
//            return true;
//        }
//
//        // 如果前序节点已经被取消/释放，则跳过
//        if (ws > 0) {
//            /*
//             * Predecessor was cancelled. Skip over predecessors and
//             * indicate retry.
//             */
//            do {
//                pred = pred.prev;
//                node.prev = pred;
//            } while (pred.waitStatus > 0);
//            pred.next = node;
//        }
//        // 设置前序节点的等待状态为signal
//        else {
//            /*
//             * waitStatus must be 0 or PROPAGATE.  Indicate that we
//             * need a signal, but don't park yet.  Caller will need to
//             * retry to make sure it cannot acquire before parking.
//             */
//            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
//        }
//
//        return false;
//    }
//
//    /**
//     * 阻塞当前线程，并检查当前线程是否已经被中断
//     * Convenience method to park and then check if interrupted
//     */
//    private final boolean parkAndCheckInterrupt() {
//        LockSupport.park(this);
//        return Thread.interrupted();
//    }
//
//    /**
//     * 中断当前线程
//     * Convenience method to interrupt current thread.
//     */
//    static void selfInterrupt() {
//        Thread.currentThread().interrupt();
//    }
//
//    /**
//     * 取消节点
//     * 1. 取消当前节点
//     * 2. 如果是队尾，更新队尾
//     * 3. 如果前驱节点不是队首，连接前驱和后继，并设置前驱的等待状态是signal
//     * 4. 如果前驱节点是队首，唤醒后继节点
//     */
//    private void cancelAcquire(Node node) {
//        // 节点不存在
//        if (node == null) {
//            return;
//        }
//
//        // 取消节点的线程
//        node.thread = null;
//
//        // 查找未取消的前序节点，跳过已取消的节点
//        Node pred = node.prev;
//        while (pred.waitStatus > 0) {
//            pred = pred.prev;
//            node.prev = pred;
//        }
//
//        // 缓存前序节点的后继
//        // predNext is the apparent node to unsplice. CASes below will
//        // fail if not, in which case, we lost race vs another cancel
//        // or signal, so no further action is necessary.
//        Node predNext = pred.next;
//
//        // 当前节点的等待状态设置为已取消
//        // Can use unconditional write instead of CAS here.
//        // After this atomic step, other Nodes can skip past us.
//        // Before, we are free of interference from other threads.
//        node.waitStatus = Node.CANCELLED;
//
//        // 如果当前节点是队尾，则更新队尾
//        if (node == tail && compareAndSetTail(node, pred)) {
//            // 设置队尾的后继为null
//            compareAndSetNext(pred, predNext, null);
//        } else {
//            // 前序节点的等待状态设置成signal，因为后面还有节点
//            // If successor needs signal, try to set pred's next-link
//            // so it will get one. Otherwise wake it up to propagate.
//            int ws = pred.waitStatus;
//            if (pred != head &&
//                    (ws == Node.SIGNAL ||
//                            (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
//                    pred.thread != null) {
//                // 更新前序节点的后继
//                Node next = node.next;
//                if (next != null && next.waitStatus <= 0) {
//                    compareAndSetNext(pred, predNext, next);
//                }
//            } else {
//                // 如果前序节点是队首节点，则唤醒后继
//                unparkSuccessor(node);
//            }
//
//            // 设置后继为自己
//            node.next = node;
//        }
//    }
//
//    // 对外开发的主要方法
//    // Main exported methods
//
//    /**
//     * 独占式获取资源，不响应中断。
//     *
//     */
//    public final void acquire(int arg) {
//        // 先尝试获取资源，如果失败则进入同步队列
//        if (!tryAcquire(arg) &&
//                // 进入同步队列
//                acquireQueued(
//                        // 当前线程加入队尾
//                        addWaiter(Node.EXCLUSIVE), arg)) {
//            // 中断当前线程
//            selfInterrupt();
//        }
//    }
//
//    /**
//     * Acquires in exclusive mode, aborting if interrupted.
//     * Implemented by first checking interrupt status, then invoking
//     * at least once {@link #tryAcquire}, returning on
//     * success.  Otherwise the thread is queued, possibly repeatedly
//     * blocking and unblocking, invoking {@link #tryAcquire}
//     * until success or the thread is interrupted.  This method can be
//     * used to implement method {@link Lock#lockInterruptibly}.
//     *
//     * @param arg the acquire argument.  This value is conveyed to
//     *        {@link #tryAcquire} but is otherwise uninterpreted and
//     *        can represent anything you like.
//     * @throws InterruptedException if the current thread is interrupted
//     */
//    public final void acquireInterruptibly(int arg)
//            throws InterruptedException {
//        if (Thread.interrupted()) {
//            throw new InterruptedException();
//        }
//        if (!tryAcquire(arg)) {
//            doAcquireInterruptibly(arg);
//        }
//    }
//
//    /**
//     * Attempts to acquire in exclusive mode, aborting if interrupted,
//     * and failing if the given timeout elapses.  Implemented by first
//     * checking interrupt status, then invoking at least once {@link
//     * #tryAcquire}, returning on success.  Otherwise, the thread is
//     * queued, possibly repeatedly blocking and unblocking, invoking
//     * {@link #tryAcquire} until success or the thread is interrupted
//     * or the timeout elapses.  This method can be used to implement
//     * method {@link Lock#tryLock(long, TimeUnit)}.
//     *
//     * @param arg the acquire argument.  This value is conveyed to
//     *        {@link #tryAcquire} but is otherwise uninterpreted and
//     *        can represent anything you like.
//     * @param nanosTimeout the maximum number of nanoseconds to wait
//     * @return {@code true} if acquired; {@code false} if timed out
//     * @throws InterruptedException if the current thread is interrupted
//     */
//    public final boolean tryAcquireNanos(int arg, long nanosTimeout)
//            throws InterruptedException {
//        if (Thread.interrupted()) {
//            throw new InterruptedException();
//        }
//        return tryAcquire(arg) ||
//                doAcquireNanos(arg, nanosTimeout);
//    }
//
//    /**
//     * 释放锁资源。
//     */
//    public final boolean release(int arg) {
//        // 尝试释放锁资源
//        if (tryRelease(arg)) {
//            // 队首节点已经获取了锁资源
//            Node h = head;
//            if (h != null && h.waitStatus != 0) {
//                // 唤醒队首节点的后继节点
//                unparkSuccessor(h);
//            }
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Acquires in shared mode, ignoring interrupts.
//     */
//    public final void acquireShared(int arg) {
//        // 尝试获取资源
//        if (tryAcquireShared(arg) < 0) {
//            // 如果获取失败，则当前线程加入等待队列
//            doAcquireShared(arg);
//        }
//    }
//
//    /**
//     * Acquires in shared mode, aborting if interrupted.  Implemented
//     * by first checking interrupt status, then invoking at least once
//     * {@link #tryAcquireShared}, returning on success.  Otherwise the
//     * thread is queued, possibly repeatedly blocking and unblocking,
//     * invoking {@link #tryAcquireShared} until success or the thread
//     * is interrupted.
//     *
//     * @param arg the acquire argument.
//     * This value is conveyed to {@link #tryAcquireShared} but is
//     * otherwise uninterpreted and can represent anything
//     * you like.
//     * @throws InterruptedException if the current thread is interrupted
//     */
//    public final void acquireSharedInterruptibly(int arg)
//            throws InterruptedException {
//        // 当前线程已被中断
//        if (Thread.interrupted()) {
//            throw new InterruptedException();
//        }
//        // 执行一次获取资源
//        if (tryAcquireShared(arg) < 0) {
//            // 加入等待队列
//            doAcquireSharedInterruptibly(arg);
//        }
//    }
//
//    /**
//     * Attempts to acquire in shared mode, aborting if interrupted, and
//     * failing if the given timeout elapses.  Implemented by first
//     * checking interrupt status, then invoking at least once {@link
//     * #tryAcquireShared}, returning on success.  Otherwise, the
//     * thread is queued, possibly repeatedly blocking and unblocking,
//     * invoking {@link #tryAcquireShared} until success or the thread
//     * is interrupted or the timeout elapses.
//     *
//     * @param arg the acquire argument.  This value is conveyed to
//     *        {@link #tryAcquireShared} but is otherwise uninterpreted
//     *        and can represent anything you like.
//     * @param nanosTimeout the maximum number of nanoseconds to wait
//     * @return {@code true} if acquired; {@code false} if timed out
//     * @throws InterruptedException if the current thread is interrupted
//     */
//    public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout)
//            throws InterruptedException {
//        if (Thread.interrupted()) {
//            throw new InterruptedException();
//        }
//        return tryAcquireShared(arg) >= 0 ||
//                doAcquireSharedNanos(arg, nanosTimeout);
//    }
//
//    /**
//     * Releases in shared mode.  Implemented by unblocking one or more
//     * threads if {@link #tryReleaseShared} returns true.
//     *
//     * @param arg the release argument.  This value is conveyed to
//     *        {@link #tryReleaseShared} but is otherwise uninterpreted
//     *        and can represent anything you like.
//     * @return the value returned from {@link #tryReleaseShared}
//     */
//    public final boolean releaseShared(int arg) {
//        if (tryReleaseShared(arg)) {
//            doReleaseShared();
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 子类要实现的方法
//     */
//    protected boolean tryAcquire(int arg) {
//        throw new UnsupportedOperationException();
//    }
//    protected boolean tryRelease(int arg) {
//        throw new UnsupportedOperationException();
//    }
//    protected int tryAcquireShared(int arg) {
//        throw new UnsupportedOperationException();
//    }
//    protected boolean tryReleaseShared(int arg) {
//        throw new UnsupportedOperationException();
//    }
//    protected boolean isHeldExclusively() {
//        throw new UnsupportedOperationException();
//    }
//
//    /*
//     * Various flavors of acquire, varying in exclusive/shared and
//     * control modes.  Each is mostly the same, but annoyingly
//     * different.  Only a little bit of factoring is possible due to
//     * interactions of exception mechanics (including ensuring that we
//     * cancel if tryAcquire throws exception) and other control, at
//     * least not without hurting performance too much.
//     */
//
//    /**
//     * 抢占式模式
//     * 如果获取到锁资源，把当前节点设置为队首节点
//     */
//    final boolean acquireQueued(final Node node, int arg) {
//        boolean failed = true;
//        try {
//            boolean interrupted = false;
//            // 自旋
//            for (;;) {
//                // 如果前驱节点是队首节点，则尝试获取资源
//                // WHY: 此时队首节点已经获取了锁资源，后继节点可以尝试
//                final Node p = node.predecessor();
//                if (p == head && tryAcquire(arg)) {
//                    // 当前线程成功获取到锁资源，更新队首节点
//                    setHead(node);
//                    // 释放原来的队首，便于GC
//                    p.next = null;
//                    failed = false;
//                    return interrupted;
//                }
//
//                // 如果获取不到锁资源
//                // 检查是否需要阻塞当前节点
//                // 检查当前线程是否已经中断
//                if (shouldParkAfterFailedAcquire(p, node) &&
//                        parkAndCheckInterrupt()) {
//                    interrupted = true;
//                }
//            }
//        } finally {
//            if (failed) {
//                // 取消当前节点
//                cancelAcquire(node);
//            }
//        }
//    }
//
//    /**
//     * Acquires in exclusive interruptible mode.
//     * @param arg the acquire argument
//     */
//    private void doAcquireInterruptibly(int arg)
//            throws InterruptedException {
//        // 新建排他模式的节点，并加入队尾
//        final Node node = addWaiter(Node.EXCLUSIVE);
//        boolean failed = true;
//        try {
//            for (;;) {
//                // 循环找到队首，尝试获取资源
//                final Node p = node.predecessor();
//                if (p == head && tryAcquire(arg)) {
//                    // 新建节点设置为队首
//                    setHead(node);
//                    // 等待队列只有一个节点
//                    p.next = null;
//                    failed = false;
//                    return;
//                }
//                // 是否阻塞当前节点
//                if (shouldParkAfterFailedAcquire(p, node) &&
//                        parkAndCheckInterrupt()) {
//                    throw new InterruptedException();
//                }
//            }
//        } finally {
//            if (failed) {
//                cancelAcquire(node);
//            }
//        }
//    }
//
//    /**
//     * Acquires in exclusive timed mode.
//     *
//     * @param arg the acquire argument
//     * @param nanosTimeout max wait time
//     * @return {@code true} if acquired
//     */
//    private boolean doAcquireNanos(int arg, long nanosTimeout)
//            throws InterruptedException {
//        if (nanosTimeout <= 0L) {
//            return false;
//        }
//        final long deadline = System.nanoTime() + nanosTimeout;
//        final Node node = addWaiter(Node.EXCLUSIVE);
//        boolean failed = true;
//        try {
//            for (;;) {
//                final Node p = node.predecessor();
//                if (p == head && tryAcquire(arg)) {
//                    setHead(node);
//                    p.next = null;
//                    failed = false;
//                    return true;
//                }
//                nanosTimeout = deadline - System.nanoTime();
//                if (nanosTimeout <= 0L) {
//                    return false;
//                }
//                if (shouldParkAfterFailedAcquire(p, node) &&
//                        nanosTimeout > spinForTimeoutThreshold) {
//                    LockSupport.parkNanos(this, nanosTimeout);
//                }
//                if (Thread.interrupted()) {
//                    throw new InterruptedException();
//                }
//            }
//        } finally {
//            if (failed) {
//                cancelAcquire(node);
//            }
//        }
//    }
//
//    /**
//     * 获取共享资源，不响应中断
//     * Acquires in shared uninterruptible mode.
//     */
//    private void doAcquireShared(int arg) {
//        // 当前线程加入队尾
//        final Node node = addWaiter(Node.SHARED);
//
//        boolean failed = true;
//        try {
//            boolean interrupted = false;
//            for (;;) {
//                // 如果已经是第一个节点
//                final Node p = node.predecessor();
//                if (p == head) {
//                    // 尝试获取资源
//                    int r = tryAcquireShared(arg);
//                    // 成功请求到资源
//                    if (r >= 0) {
//                        // TODO
//                        setHeadAndPropagate(node, r);
//                        p.next = null;
//                        if (interrupted) {
//                            // 中断当前线程
//                            selfInterrupt();
//                        }
//                        failed = false;
//                        return;
//                    }
//                    // 获取资源失败
//                }
//                // 还不是第一个节点
//
//                // 检查是否要阻塞当前线程
//                // 如果需要则阻塞当前线程
//                // 检查当前线程是否已中断
//                if (shouldParkAfterFailedAcquire(p, node) &&
//                        parkAndCheckInterrupt()) {
//                    interrupted = true;
//                }
//            }
//        } finally {
//            if (failed) {
//                // 操作失败，取消当前节点
//                cancelAcquire(node);
//            }
//        }
//    }
//
//    /**
//     * Acquires in shared interruptible mode.
//     * @param arg the acquire argument
//     */
//    private void doAcquireSharedInterruptibly(int arg)
//            throws InterruptedException {
//        final Node node = addWaiter(Node.SHARED);
//        boolean failed = true;
//        try {
//            for (;;) {
//                final Node p = node.predecessor();
//                if (p == head) {
//                    int r = tryAcquireShared(arg);
//                    if (r >= 0) {
//                        setHeadAndPropagate(node, r);
//                        p.next = null;
//                        failed = false;
//                        return;
//                    }
//                }
//                if (shouldParkAfterFailedAcquire(p, node) &&
//                        parkAndCheckInterrupt()) {
//                    throw new InterruptedException();
//                }
//            }
//        } finally {
//            if (failed) {
//                cancelAcquire(node);
//            }
//        }
//    }
//
//    /**
//     * Acquires in shared timed mode.
//     *
//     * @param arg the acquire argument
//     * @param nanosTimeout max wait time
//     * @return {@code true} if acquired
//     */
//    private boolean doAcquireSharedNanos(int arg, long nanosTimeout)
//            throws InterruptedException {
//        if (nanosTimeout <= 0L) {
//            return false;
//        }
//        final long deadline = System.nanoTime() + nanosTimeout;
//        final Node node = addWaiter(Node.SHARED);
//        boolean failed = true;
//        try {
//            for (;;) {
//                final Node p = node.predecessor();
//                if (p == head) {
//                    int r = tryAcquireShared(arg);
//                    if (r >= 0) {
//                        setHeadAndPropagate(node, r);
//                        p.next = null;
//                        failed = false;
//                        return true;
//                    }
//                }
//                nanosTimeout = deadline - System.nanoTime();
//                if (nanosTimeout <= 0L) {
//                    return false;
//                }
//                if (shouldParkAfterFailedAcquire(p, node) &&
//                        nanosTimeout > spinForTimeoutThreshold) {
//                    LockSupport.parkNanos(this, nanosTimeout);
//                }
//                if (Thread.interrupted()) {
//                    throw new InterruptedException();
//                }
//            }
//        } finally {
//            if (failed) {
//                cancelAcquire(node);
//            }
//        }
//    }
//
//    // Internal support methods for Conditions
//
//    /**
//     * Returns true if a node, always one that was initially placed on
//     * a condition queue, is now waiting to reacquire on sync queue.
//     */
//    final boolean isOnSyncQueue(Node node) {
//        if (node.waitStatus == Node.CONDITION || node.prev == null) {
//            return false;
//        }
//        // If has successor, it must be on queue
//        if (node.next != null) {
//            return true;
//        }
//        /*
//         * node.prev can be non-null, but not yet on queue because
//         * the CAS to place it on queue can fail. So we have to
//         * traverse from tail to make sure it actually made it.  It
//         * will always be near the tail in calls to this method, and
//         * unless the CAS failed (which is unlikely), it will be
//         * there, so we hardly ever traverse much.
//         */
//        return findNodeFromTail(node);
//    }
//
//    /**
//     * Returns true if node is on sync queue by searching backwards from tail.
//     * Called only when needed by isOnSyncQueue.
//     */
//    private boolean findNodeFromTail(Node node) {
//        Node t = tail;
//        for (;;) {
//            if (t == node) {
//                return true;
//            }
//            if (t == null) {
//                return false;
//            }
//            t = t.prev;
//        }
//    }
//
//    /**
//     * Transfers a node from a condition queue onto sync queue.
//     * Returns true if successful.
//     */
//    final boolean transferForSignal(Node node) {
//        /*
//         * If cannot change waitStatus, the node has been cancelled.
//         */
//        if (!compareAndSetWaitStatus(node, Node.CONDITION, 0)) {
//            return false;
//        }
//
//        /*
//         * Splice onto queue and try to set waitStatus of predecessor to
//         * indicate that thread is (probably) waiting. If cancelled or
//         * attempt to set waitStatus fails, wake up to resync (in which
//         * case the waitStatus can be transiently and harmlessly wrong).
//         */
//        Node p = enq(node);
//        int ws = p.waitStatus;
//        if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL)) {
//            LockSupport.unpark(node.thread);
//        }
//        return true;
//    }
//
//    /**
//     * Transfers node, if necessary, to sync queue after a cancelled wait.
//     * Returns true if thread was cancelled before being signalled.
//     */
//    final boolean transferAfterCancelledWait(Node node) {
//        // 节点的等待状态从等待条件到0
//        if (compareAndSetWaitStatus(node, Node.CONDITION, 0)) {
//            enq(node);
//            return true;
//        }
//        /*
//         * If we lost out to a signal(), then we can't proceed
//         * until it finishes its enq().  Cancelling during an
//         * incomplete transfer is both rare and transient, so just
//         * spin.
//         */
//        while (!isOnSyncQueue(node)) {
//            Thread.yield();
//        }
//        return false;
//    }
//
//    /**
//     * Invokes release with current state value; returns saved state.
//     * Cancels node and throws exception on failure.
//     * @param node the condition node for this wait
//     * @return previous sync state
//     */
//    final int fullyRelease(Node node) {
//        boolean failed = true;
//        try {
//            int savedState = getState();
//            if (release(savedState)) {
//                failed = false;
//                return savedState;
//            } else {
//                throw new IllegalMonitorStateException();
//            }
//        } finally {
//            if (failed) {
//                node.waitStatus = Node.CANCELLED;
//            }
//        }
//    }
//
//    // Instrumentation methods for conditions
//
//    /**
//     * Queries whether the given ConditionObject
//     * uses this synchronizer as its lock.
//     *
//     * @param condition the condition
//     * @return {@code true} if owned
//     * @throws NullPointerException if the condition is null
//     */
////    public final boolean owns(ConditionObject condition) {
////        return condition.isOwnedBy(this);
////    }
//
//    /**
//     * Queries whether any threads are waiting on the given condition
//     * associated with this synchronizer. Note that because timeouts
//     * and interrupts may occur at any time, a {@code true} return
//     * does not guarantee that a future {@code signal} will awaken
//     * any threads.  This method is designed primarily for use in
//     * monitoring of the system state.
//     *
//     * @param condition the condition
//     * @return {@code true} if there are any waiting threads
//     * @throws IllegalMonitorStateException if exclusive synchronization
//     *         is not held
//     * @throws IllegalArgumentException if the given condition is
//     *         not associated with this synchronizer
//     * @throws NullPointerException if the condition is null
//     */
//    public final boolean hasWaiters(ConditionObject condition) {
//        if (!owns(condition)) {
//            throw new IllegalArgumentException("Not owner");
//        }
//        return condition.hasWaiters();
//    }
//
//    /**
//     * Returns an estimate of the number of threads waiting on the
//     * given condition associated with this synchronizer. Note that
//     * because timeouts and interrupts may occur at any time, the
//     * estimate serves only as an upper bound on the actual number of
//     * waiters.  This method is designed for use in monitoring of the
//     * system state, not for synchronization control.
//     *
//     * @param condition the condition
//     * @return the estimated number of waiting threads
//     * @throws IllegalMonitorStateException if exclusive synchronization
//     *         is not held
//     * @throws IllegalArgumentException if the given condition is
//     *         not associated with this synchronizer
//     * @throws NullPointerException if the condition is null
//     */
//    public final int getWaitQueueLength(ConditionObject condition) {
//        if (!owns(condition)) {
//            throw new IllegalArgumentException("Not owner");
//        }
//        return condition.getWaitQueueLength();
//    }
//
//    /**
//     * Returns a collection containing those threads that may be
//     * waiting on the given condition associated with this
//     * synchronizer.  Because the actual set of threads may change
//     * dynamically while constructing this result, the returned
//     * collection is only a best-effort estimate. The elements of the
//     * returned collection are in no particular order.
//     *
//     * @param condition the condition
//     * @return the collection of threads
//     * @throws IllegalMonitorStateException if exclusive synchronization
//     *         is not held
//     * @throws IllegalArgumentException if the given condition is
//     *         not associated with this synchronizer
//     * @throws NullPointerException if the condition is null
//     */
//    public final Collection<Thread> getWaitingThreads(ConditionObject condition) {
//        if (!owns(condition)) {
//            throw new IllegalArgumentException("Not owner");
//        }
//        return condition.getWaitingThreads();
//    }
//
//    /**
//     * CAS支持
//     */
//    private static final Unsafe unsafe = Unsafe.getUnsafe();
//    private static final long stateOffset;
//    private static final long headOffset;
//    private static final long tailOffset;
//    private static final long waitStatusOffset;
//    private static final long nextOffset;
//
//    static {
//        try {
//            stateOffset = unsafe.objectFieldOffset
//                    (java.util.concurrent.locks.AbstractQueuedSynchronizer.class.getDeclaredField("state"));
//            headOffset = unsafe.objectFieldOffset
//                    (java.util.concurrent.locks.AbstractQueuedSynchronizer.class.getDeclaredField("head"));
//            tailOffset = unsafe.objectFieldOffset
//                    (java.util.concurrent.locks.AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
//            waitStatusOffset = unsafe.objectFieldOffset
//                    (Node.class.getDeclaredField("waitStatus"));
//            nextOffset = unsafe.objectFieldOffset
//                    (Node.class.getDeclaredField("next"));
//        } catch (Exception ex) { throw new Error(ex); }
//    }
//
//    /**
//     * CAS head field. Used only by enq.
//     */
//    private final boolean compareAndSetHead(Node update) {
//        return unsafe.compareAndSwapObject(this, headOffset, null, update);
//    }
//    /**
//     * CAS tail field. Used only by enq.
//     */
//    private final boolean compareAndSetTail(Node expect, Node update) {
//        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
//    }
//    /**
//     * CAS waitStatus field of a node.
//     */
//    private static final boolean compareAndSetWaitStatus(Node node,
//                                                         int expect,
//                                                         int update) {
//        return unsafe.compareAndSwapInt(node, waitStatusOffset,
//                expect, update);
//    }
//    /**
//     * CAS next field of a node.
//     */
//    private static final boolean compareAndSetNext(Node node,
//                                                   Node expect,
//                                                   Node update) {
//        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
//    }
//
//    /**
//     * The number of nanoseconds for which it is faster to spin
//     * rather than to use timed park. A rough estimate suffices
//     * to improve responsiveness with very short timeouts.
//     */
//    static final long spinForTimeoutThreshold = 1000L;
//
//}
