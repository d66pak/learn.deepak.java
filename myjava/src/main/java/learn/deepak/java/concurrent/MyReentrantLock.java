package learn.deepak.java.concurrent;

/**
 * Simple reentrant lock
 * Non fair
 */
public class MyReentrantLock {

    private boolean isLocked;
    private Thread lockingThread;
    private int lockCount;
    private final Object monitor = new Object();

    public void lock() throws InterruptedException {

        synchronized (monitor) {
            while (isLocked && !isReentrant()) {
                monitor.wait();
            }

            System.out.println("Lock granted to : " + Thread.currentThread());
            isLocked = true;
            lockCount += 1;
            lockingThread = Thread.currentThread();
        }
    }

    public void unlock() {

        synchronized (monitor) {
            if (lockingThread != Thread.currentThread()) {
                throw new IllegalMonitorStateException("Thread trying to unlock is not the owner of this lock!");
            }

            lockCount -= 1;
            System.out.println("Unlocked by : " + Thread.currentThread() + " lock count : " + lockCount);
            if (lockCount <= 0) {
                lockCount = 0;
                isLocked = false;
                lockingThread = null;
                monitor.notify();
            }
        }
    }

    private boolean isReentrant() {

        return (lockingThread == Thread.currentThread()) &&
                lockCount > 0;
    }
}
