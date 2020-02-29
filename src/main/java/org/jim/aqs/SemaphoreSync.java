//package org.jim.aqs;
//
//class SemaphoreSync extends AbstractQueuedSynchronizer {
//
//    SemaphoreSync(int permits) {
//        setState(permits);
//    }
//    final int getPermits() {
//        return getState();
//    }
//
//
//    /**
//     * 请求获取资源
//     *
//     */
//    @Override
//    protected int tryAcquireShared(int acquires) {
//        return nonfairTryAcquireShared(acquires);
//    }
//
//    final int nonfairTryAcquireShared(int acquires) {
//        for (;;) {
//            int available = getState();
//            int remaining = available - acquires;
//            if (remaining < 0 ||
//                    compareAndSetState(available, remaining)) {
//                return remaining;
//            }
//        }
//    }
//
//    /**
//     * 请求释放资源
//     *
//     */
//    @Override
//    protected final boolean tryReleaseShared(int releases) {
//        for (;;) {
//            int current = getState();
//            int next = current + releases;
//            if (next < current) {
//                throw new Error("Maximum permit count exceeded");
//            }
//            if (compareAndSetState(current, next)) {
//                return true;
//            }
//        }
//    }
//
//}
