package learn.deepak.java.concurrent;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple read write lock
 * - Writer high priority
 * - Parallel reads
 * - Single write
 * - Read - read reentrant
 * - Write - write reentrant
 * - Read - write reentrant
 * - Write - read reentrant
 */
public class MyReadWriteLock {

    private final Object monitor = new Object();
    private int pendingWriteRequests;
    private int totalReadRequests;
    private int totalWriteRequests;
    private Thread writingThread;
    private Map<Thread, Integer> readRequestsPerThreadMap = new HashMap<>();

    public void readLock() throws InterruptedException {

        synchronized (monitor) {
            while (!allowRead(Thread.currentThread())) {
                monitor.wait();
            }

            // Read lock is available
            incrementReadRequests(Thread.currentThread());
            System.out.println("Read granted to : " + Thread.currentThread() + " RR : " + totalReadRequests +
                    " WR : " + totalWriteRequests + " PWR : " + pendingWriteRequests);
        }
    }

    public void unlockRead() {

        synchronized (monitor) {
            decrementReadRequests(Thread.currentThread());

            System.out.println("Read unlocked by : " + Thread.currentThread() + " RR : " + totalReadRequests +
                    " WR : " + totalWriteRequests + " PWR : " + pendingWriteRequests);

            if (pendingWriteRequests > 0) {
                monitor.notifyAll();
            }
        }
    }

    public void lockWrite() throws InterruptedException {

        synchronized (monitor) {
            pendingWriteRequests += 1;

            while (!allowWrite(Thread.currentThread())) {
                monitor.wait();
            }

            // Write lock is available
            pendingWriteRequests -= 1;
            totalWriteRequests += 1;
            writingThread = Thread.currentThread();
            System.out.println("Write granted to : " + Thread.currentThread() + " RR : " + totalReadRequests +
                    " WR : " + totalWriteRequests + " PWR : " + pendingWriteRequests);
        }
    }

    public void unlockWrite() {

        synchronized (monitor) {
            totalWriteRequests -= 1;

            if (totalWriteRequests <= 0) {
                totalWriteRequests = 0;
                writingThread = null;
            }

            System.out.println("Write unlocked by : " + Thread.currentThread() + " RR : " + totalReadRequests +
                    " WR : " + totalWriteRequests + " PWR : " + pendingWriteRequests);
            monitor.notifyAll();
        }
    }

    private boolean allowRead(Thread thread) {

        if (isWriteReentrant(thread)) return true;

        if (isReadReentrant(thread)) return totalWriteRequests <= 0;

        return totalWriteRequests <= 0 && pendingWriteRequests <= 0;
    }

    private boolean allowWrite(Thread thread) {

        if (isWriteReentrant(thread)) return true;

        if (isReadReentrant(thread)) {
            int currentThreadRequestCount = getRequestCount(Thread.currentThread());
            int otherReadRequests = totalReadRequests - currentThreadRequestCount;
            return (otherReadRequests <= 0);
        }

        return (totalWriteRequests <= 0 && totalReadRequests <= 0);
    }

    private boolean isReadReentrant(Thread thread) {
        return readRequestsPerThreadMap.containsKey(thread);
    }

    private boolean isWriteReentrant(Thread thread) {
        return writingThread == thread;
    }

    public Integer getRequestCount(Thread thread) {

        Integer threadRequests = readRequestsPerThreadMap.get(thread);
        if (threadRequests == null) {
            return 0;
        } else {
            return threadRequests;
        }
    }

    public void incrementReadRequests(Thread thread) {

        // Increase total read requests
        totalReadRequests += 1;

        // Increase read request of particular thread
        int requestCount = getRequestCount(Thread.currentThread());
        readRequestsPerThreadMap.put(Thread.currentThread(), ++requestCount);
    }

    void decrementReadRequests(Thread thread) {

        // Decrease total read requests
        totalReadRequests -= 1;

        // Decrease read request of particular thread
        int requestCount = getRequestCount(thread);
        requestCount -= 1;
        if (requestCount <= 0) {
            readRequestsPerThreadMap.remove(thread);
        } else {
            readRequestsPerThreadMap.put(thread, requestCount);
        }
    }
}
