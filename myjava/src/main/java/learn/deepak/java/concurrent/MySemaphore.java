package learn.deepak.java.concurrent;

/**
 * Semaphore
 * - counting
 * - Non fair
 */
public class MySemaphore {

    private final Object monitor = new Object();
    private boolean signal = true;

    private int maxPermits = Integer.MAX_VALUE;
    private int availablePermits;

    public MySemaphore() {
        availablePermits = maxPermits;
    }

    public MySemaphore(int permits) {
        maxPermits = permits;
        availablePermits = maxPermits;
    }

    public void acquire() throws InterruptedException {

        synchronized (monitor) {
            while (availablePermits <= 0) {
                monitor.wait();
            }

            // Permit should be available now
            availablePermits -= 1;
            System.out.println("Permit granted to : " + Thread.currentThread() + " remaining availablePermits : " + availablePermits);
        }
    }

    // Blocks until all permits are available
    public void acquire(int permits) throws InterruptedException {

        synchronized (monitor) {
            while (availablePermits < permits) {
                monitor.wait();
            }

            // Permits are available now
            availablePermits -= permits;
            System.out.println("Permit granted to : " + Thread.currentThread() + " remaining availablePermits : " + availablePermits);
        }
    }

    public void release() {

        synchronized (monitor) {

            if (!arePermitsWithinLimit(1)) {
                throw new IllegalMonitorStateException("Illegal permit release!");
            }
            availablePermits += 1;
            System.out.println("Permit released by : " + Thread.currentThread() + " available availablePermits : " + availablePermits);
            monitor.notify();
        }
    }

    public void release(int permits) {

        synchronized (monitor) {
            if (!arePermitsWithinLimit(permits)) {
                throw new IllegalMonitorStateException("Illegal permit release!");
            }

            while (permits > 0) {
                release();
                permits -= 1;
            }
        }
    }

    private boolean arePermitsWithinLimit(int permits) {

        return availablePermits + permits <= maxPermits;
    }
}
