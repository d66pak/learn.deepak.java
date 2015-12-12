package learn.deepak.java.concurrent;

/**
 * Wait() and notify() implementation
 */
public class MyWaitNotify {

    private final Object monitorObject = new Object();
    private boolean keepWaiting = true;

    public void doWait() {

        synchronized (monitorObject) {
            while (keepWaiting) {
                try {
                    monitorObject.wait();
                } catch (InterruptedException e) {
                    System.out.println("Wait interrupted! keepWaiting : " + keepWaiting);
                }
            }

            // Lock has been acquired and keepWaiting = false
            // set keepWaiting for next use
            keepWaiting = true;
        }
    }

    public void doNotify() {

        synchronized (monitorObject) {

            // stop waiting
            keepWaiting = false;

            // convey 'stop waiting'
            monitorObject.notify();
        }
    }
}
