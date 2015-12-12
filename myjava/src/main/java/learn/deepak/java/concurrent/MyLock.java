package learn.deepak.java.concurrent;

/**
 * Non reentrant
 * Non fair
 */
public class MyLock {

    private boolean locked = false;
    private Thread lockingThread;
    private final Object monitor = new Object();

    public void lock() throws InterruptedException {

        synchronized (monitor) {
            while (locked) {
                monitor.wait();
            }

            /**
             * Lock has become available now or
             * lock was already available
             */
            System.out.println("Lock granted to : " + Thread.currentThread());
            locked = true;
            lockingThread = Thread.currentThread();
        }
    }

    public void unlock() {

        synchronized (monitor) {
            if (lockingThread != Thread.currentThread()) {
                throw new IllegalMonitorStateException("Thread trying to unlock is not the owner of this lock!");
            }

            System.out.println("Unlocked by : " + Thread.currentThread());
            locked = false;
            lockingThread = null;
            monitor.notify();
        }
    }

}
