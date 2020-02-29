//package org.jim.aqs;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.LockSupport;
//
///**
// * Condition implementation for a {@link
// * java.util.concurrent.locks.AbstractQueuedSynchronizer} serving as the basis of a {@link
// * Lock} implementation.
// *
// * <p>Method documentation for this class describes mechanics,
// * not behavioral specifications from the point of view of Lock
// * and Condition users. Exported versions of this class will in
// * general need to be accompanied by documentation describing
// * condition semantics that rely on those of the associated
// * {@code AbstractQueuedSynchronizer}.
// *
// * <p>This class is Serializable, but all fields are transient,
// * so deserialized conditions have no waiters.
// */
//public class ConditionObject implements Condition, java.io.Serializable {
//    private static final long serialVersionUID = 1173984872572414699L;
//    /** First node of condition queue. */
//    private transient Node firstWaiter;
//    /** Last node of condition queue. */
//    private transient Node lastWaiter;
//
//    /**
//     * Creates a new {@code ConditionObject} instance.
//     */
//    public ConditionObject() { }
//
//    // Internal methods
//
//    /**
//     * Adds a new waiter to wait queue.
//     * @return its new wait node
//     */
//    private Node addConditionWaiter() {
//        Node t = lastWaiter;
//        // If lastWaiter is cancelled, clean out.
//        if (t != null && t.waitStatus != Node.CONDITION) {
//            unlinkCancelledWaiters();
//            t = lastWaiter;
//        }
//        Node node = new Node(Thread.currentThread(), Node.CONDITION);
//        if (t == null)
//            firstWaiter = node;
//        else
//            t.nextWaiter = node;
//        lastWaiter = node;
//        return node;
//    }
//
//    /**
//     * Removes and transfers nodes until hit non-cancelled one or
//     * null. Split out from signal in part to encourage compilers
//     * to inline the case of no waiters.
//     * @param first (non-null) the first node on condition queue
//     */
//    private void doSignal(Node first) {
//        do {
//            if ( (firstWaiter = first.nextWaiter) == null)
//                lastWaiter = null;
//            first.nextWaiter = null;
//        } while (!transferForSignal(first) &&
//                (first = firstWaiter) != null);
//    }
//
//    /**
//     * Removes and transfers all nodes.
//     * @param first (non-null) the first node on condition queue
//     */
//    private void doSignalAll(Node first) {
//        lastWaiter = firstWaiter = null;
//        do {
//            Node next = first.nextWaiter;
//            first.nextWaiter = null;
//            transferForSignal(first);
//            first = next;
//        } while (first != null);
//    }
//
//    /**
//     * Unlinks cancelled waiter nodes from condition queue.
//     * Called only while holding lock. This is called when
//     * cancellation occurred during condition wait, and upon
//     * insertion of a new waiter when lastWaiter is seen to have
//     * been cancelled. This method is needed to avoid garbage
//     * retention in the absence of signals. So even though it may
//     * require a full traversal, it comes into play only when
//     * timeouts or cancellations occur in the absence of
//     * signals. It traverses all nodes rather than stopping at a
//     * particular target to unlink all pointers to garbage nodes
//     * without requiring many re-traversals during cancellation
//     * storms.
//     */
//    private void unlinkCancelledWaiters() {
//        Node t = firstWaiter;
//        Node trail = null;
//        while (t != null) {
//            Node next = t.nextWaiter;
//            if (t.waitStatus != Node.CONDITION) {
//                t.nextWaiter = null;
//                if (trail == null)
//                    firstWaiter = next;
//                else
//                    trail.nextWaiter = next;
//                if (next == null)
//                    lastWaiter = trail;
//            }
//            else
//                trail = t;
//            t = next;
//        }
//    }
//
//    // public methods
//
//    /**
//     * Moves the longest-waiting thread, if one exists, from the
//     * wait queue for this condition to the wait queue for the
//     * owning lock.
//     *
//     * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
//     *         returns {@code false}
//     */
//    public final void signal() {
//        if (!isHeldExclusively())
//            throw new IllegalMonitorStateException();
//        Node first = firstWaiter;
//        if (first != null)
//            doSignal(first);
//    }
//
//    /**
//     * Moves all threads from the wait queue for this condition to
//     * the wait queue for the owning lock.
//     *
//     * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
//     *         returns {@code false}
//     */
//    public final void signalAll() {
//        if (!isHeldExclusively())
//            throw new IllegalMonitorStateException();
//        Node first = firstWaiter;
//        if (first != null)
//            doSignalAll(first);
//    }
//
//    /**
//     * Implements uninterruptible condition wait.
//     * <ol>
//     * <li> Save lock state returned by {@link #getState}.
//     * <li> Invoke {@link #release} with saved state as argument,
//     *      throwing IllegalMonitorStateException if it fails.
//     * <li> Block until signalled.
//     * <li> Reacquire by invoking specialized version of
//     *      {@link #acquire} with saved state as argument.
//     * </ol>
//     */
//    public final void awaitUninterruptibly() {
//        Node node = addConditionWaiter();
//        int savedState = fullyRelease(node);
//        boolean interrupted = false;
//        while (!isOnSyncQueue(node)) {
//            LockSupport.park(this);
//            if (Thread.interrupted())
//                interrupted = true;
//        }
//        if (acquireQueued(node, savedState) || interrupted)
//            selfInterrupt();
//    }
//
//    /*
//     * For interruptible waits, we need to track whether to throw
//     * InterruptedException, if interrupted while blocked on
//     * condition, versus reinterrupt current thread, if
//     * interrupted while blocked waiting to re-acquire.
//     */
//
//    /** Mode meaning to reinterrupt on exit from wait */
//    private static final int REINTERRUPT =  1;
//    /** Mode meaning to throw InterruptedException on exit from wait */
//    private static final int THROW_IE    = -1;
//
//    /**
//     * Checks for interrupt, returning THROW_IE if interrupted
//     * before signalled, REINTERRUPT if after signalled, or
//     * 0 if not interrupted.
//     */
//    private int checkInterruptWhileWaiting(Node node) {
//        return Thread.interrupted() ?
//                (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) :
//                0;
//    }
//
//    /**
//     * Throws InterruptedException, reinterrupts current thread, or
//     * does nothing, depending on mode.
//     */
//    private void reportInterruptAfterWait(int interruptMode)
//            throws InterruptedException {
//        if (interruptMode == THROW_IE)
//            throw new InterruptedException();
//        else if (interruptMode == REINTERRUPT)
//            selfInterrupt();
//    }
//
//    /**
//     * Implements interruptible condition wait.
//     * <ol>
//     * <li> If current thread is interrupted, throw InterruptedException.
//     * <li> Save lock state returned by {@link #getState}.
//     * <li> Invoke {@link #release} with saved state as argument,
//     *      throwing IllegalMonitorStateException if it fails.
//     * <li> Block until signalled or interrupted.
//     * <li> Reacquire by invoking specialized version of
//     *      {@link #acquire} with saved state as argument.
//     * <li> If interrupted while blocked in step 4, throw InterruptedException.
//     * </ol>
//     */
//    public final void await() throws InterruptedException {
//        if (Thread.interrupted())
//            throw new InterruptedException();
//        Node node = addConditionWaiter();
//        int savedState = fullyRelease(node);
//        int interruptMode = 0;
//        while (!isOnSyncQueue(node)) {
//            LockSupport.park(this);
//            if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
//                break;
//        }
//        if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
//            interruptMode = REINTERRUPT;
//        if (node.nextWaiter != null) // clean up if cancelled
//            unlinkCancelledWaiters();
//        if (interruptMode != 0)
//            reportInterruptAfterWait(interruptMode);
//    }
//
//    /**
//     * Implements timed condition wait.
//     * <ol>
//     * <li> If current thread is interrupted, throw InterruptedException.
//     * <li> Save lock state returned by {@link #getState}.
//     * <li> Invoke {@link #release} with saved state as argument,
//     *      throwing IllegalMonitorStateException if it fails.
//     * <li> Block until signalled, interrupted, or timed out.
//     * <li> Reacquire by invoking specialized version of
//     *      {@link #acquire} with saved state as argument.
//     * <li> If interrupted while blocked in step 4, throw InterruptedException.
//     * </ol>
//     */
//    public final long awaitNanos(long nanosTimeout)
//            throws InterruptedException {
//        if (Thread.interrupted())
//            throw new InterruptedException();
//        Node node = addConditionWaiter();
//        int savedState = fullyRelease(node);
//        final long deadline = System.nanoTime() + nanosTimeout;
//        int interruptMode = 0;
//        while (!isOnSyncQueue(node)) {
//            if (nanosTimeout <= 0L) {
//                transferAfterCancelledWait(node);
//                break;
//            }
//            if (nanosTimeout >= spinForTimeoutThreshold)
//                LockSupport.parkNanos(this, nanosTimeout);
//            if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
//                break;
//            nanosTimeout = deadline - System.nanoTime();
//        }
//        if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
//            interruptMode = REINTERRUPT;
//        if (node.nextWaiter != null)
//            unlinkCancelledWaiters();
//        if (interruptMode != 0)
//            reportInterruptAfterWait(interruptMode);
//        return deadline - System.nanoTime();
//    }
//
//    /**
//     * Implements absolute timed condition wait.
//     * <ol>
//     * <li> If current thread is interrupted, throw InterruptedException.
//     * <li> Save lock state returned by {@link #getState}.
//     * <li> Invoke {@link #release} with saved state as argument,
//     *      throwing IllegalMonitorStateException if it fails.
//     * <li> Block until signalled, interrupted, or timed out.
//     * <li> Reacquire by invoking specialized version of
//     *      {@link #acquire} with saved state as argument.
//     * <li> If interrupted while blocked in step 4, throw InterruptedException.
//     * <li> If timed out while blocked in step 4, return false, else true.
//     * </ol>
//     */
//    public final boolean awaitUntil(Date deadline)
//            throws InterruptedException {
//        long abstime = deadline.getTime();
//        if (Thread.interrupted())
//            throw new InterruptedException();
//        Node node = addConditionWaiter();
//        int savedState = fullyRelease(node);
//        boolean timedout = false;
//        int interruptMode = 0;
//        while (!isOnSyncQueue(node)) {
//            if (System.currentTimeMillis() > abstime) {
//                timedout = transferAfterCancelledWait(node);
//                break;
//            }
//            LockSupport.parkUntil(this, abstime);
//            if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
//                break;
//        }
//        if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
//            interruptMode = REINTERRUPT;
//        if (node.nextWaiter != null)
//            unlinkCancelledWaiters();
//        if (interruptMode != 0)
//            reportInterruptAfterWait(interruptMode);
//        return !timedout;
//    }
//
//    /**
//     * Implements timed condition wait.
//     * <ol>
//     * <li> If current thread is interrupted, throw InterruptedException.
//     * <li> Save lock state returned by {@link #getState}.
//     * <li> Invoke {@link #release} with saved state as argument,
//     *      throwing IllegalMonitorStateException if it fails.
//     * <li> Block until signalled, interrupted, or timed out.
//     * <li> Reacquire by invoking specialized version of
//     *      {@link #acquire} with saved state as argument.
//     * <li> If interrupted while blocked in step 4, throw InterruptedException.
//     * <li> If timed out while blocked in step 4, return false, else true.
//     * </ol>
//     */
//    public final boolean await(long time, TimeUnit unit)
//            throws InterruptedException {
//        long nanosTimeout = unit.toNanos(time);
//        if (Thread.interrupted())
//            throw new InterruptedException();
//        Node node = addConditionWaiter();
//        int savedState = fullyRelease(node);
//        final long deadline = System.nanoTime() + nanosTimeout;
//        boolean timedout = false;
//        int interruptMode = 0;
//        while (!isOnSyncQueue(node)) {
//            if (nanosTimeout <= 0L) {
//                timedout = transferAfterCancelledWait(node);
//                break;
//            }
//            if (nanosTimeout >= spinForTimeoutThreshold)
//                LockSupport.parkNanos(this, nanosTimeout);
//            if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
//                break;
//            nanosTimeout = deadline - System.nanoTime();
//        }
//        if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
//            interruptMode = REINTERRUPT;
//        if (node.nextWaiter != null)
//            unlinkCancelledWaiters();
//        if (interruptMode != 0)
//            reportInterruptAfterWait(interruptMode);
//        return !timedout;
//    }
//
//    //  support for instrumentation
//
//    /**
//     * Returns true if this condition was created by the given
//     * synchronization object.
//     *
//     * @return {@code true} if owned
//     */
//    final boolean isOwnedBy(java.util.concurrent.locks.AbstractQueuedSynchronizer sync) {
//        return sync == java.util.concurrent.locks.AbstractQueuedSynchronizer.this;
//    }
//
//    /**
//     * Queries whether any threads are waiting on this condition.
//     * Implements {@link java.util.concurrent.locks.AbstractQueuedSynchronizer#hasWaiters(java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject)}.
//     *
//     * @return {@code true} if there are any waiting threads
//     * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
//     *         returns {@code false}
//     */
//    protected final boolean hasWaiters() {
//        if (!isHeldExclusively())
//            throw new IllegalMonitorStateException();
//        for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
//            if (w.waitStatus == Node.CONDITION)
//                return true;
//        }
//        return false;
//    }
//
//    /**
//     * Returns an estimate of the number of threads waiting on
//     * this condition.
//     * Implements {@link java.util.concurrent.locks.AbstractQueuedSynchronizer#getWaitQueueLength(java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject)}.
//     *
//     * @return the estimated number of waiting threads
//     * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
//     *         returns {@code false}
//     */
//    protected final int getWaitQueueLength() {
//        if (!isHeldExclusively())
//            throw new IllegalMonitorStateException();
//        int n = 0;
//        for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
//            if (w.waitStatus == Node.CONDITION)
//                ++n;
//        }
//        return n;
//    }
//
//    /**
//     * Returns a collection containing those threads that may be
//     * waiting on this Condition.
//     * Implements {@link java.util.concurrent.locks.AbstractQueuedSynchronizer#getWaitingThreads(java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject)}.
//     *
//     * @return the collection of threads
//     * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
//     *         returns {@code false}
//     */
//    protected final Collection<Thread> getWaitingThreads() {
//        if (!isHeldExclusively())
//            throw new IllegalMonitorStateException();
//        ArrayList<Thread> list = new ArrayList<Thread>();
//        for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
//            if (w.waitStatus == Node.CONDITION) {
//                Thread t = w.thread;
//                if (t != null)
//                    list.add(t);
//            }
//        }
//        return list;
//    }
//}
