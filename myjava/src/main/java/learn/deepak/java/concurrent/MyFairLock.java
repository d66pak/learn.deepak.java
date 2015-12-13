package learn.deepak.java.concurrent;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Fair lock
 *
 * Each thread has its own WaitNotify object which are maintained in a queue
 */
public class MyFairLock {

    private final Object monitor = new Object();
    private final Queue<MyWaitNotify> waitQueue = new ArrayDeque<>();
    private Thread lockingThread;

    private boolean isLocked = false;
    private int lockCount;

    public void lock() throws InterruptedException {

        MyWaitNotify threadWait = new MyWaitNotify();
        synchronized (monitor) {
            waitQueue.offer(threadWait);
        }

        boolean isLockedForThisThread = true;
        while (isLockedForThisThread) {
            synchronized (monitor) {
                boolean reentered = hasReentered();
                isLockedForThisThread = !reentered && (isLocked || threadWait != waitQueue.peek());

                if (!isLockedForThisThread) {
                    isLocked = true;
                    lockingThread = Thread.currentThread();
                    lockCount += 1;
                    System.out.println("Lock granted to : " + Thread.currentThread() + " lock count : " + lockCount);

                    // Remove the threadWait in case the lock is free or
                    // reentered
                    waitQueue.poll();
                }
            }

            // doWait() must be called outside synchronized because monitor holding lock is not
            // the one on which wait is called
            if (isLockedForThisThread) {
                threadWait.doWait();
            }
        }
    }

    private boolean hasReentered() {

        return isLocked && lockCount > 0 && lockingThread == Thread.currentThread();
    }

    public void unlock() {

        synchronized (monitor) {
            if (lockingThread != Thread.currentThread()) {
                throw new IllegalMonitorStateException("Thread trying to unlock is not the owner of this lock!");
            }

            lockCount -= 1;
            if (lockCount <= 0) {
                lockCount = 0;
                System.out.println("Unlocked by : " + Thread.currentThread() + " lock count : " + lockCount);
                isLocked = false;
                lockingThread = null;
                if (!waitQueue.isEmpty()) {
                    waitQueue.peek().doNotify();
                }
            }
        }
    }
}
