package learn.deepak.java.concurrent;

/**
 * Created by dtelkar on 4/16/14.
 */
public class MyInterruptedThread implements Runnable {

    private String[] messages = {
            "Mares eat oats",
            "Dogs eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
    };

    public static void logMsg(String msg) {

        System.out.format("%s : %s%n", Thread.currentThread().getName(), msg);
    }

    @Override
    public void run() {
        try {
            for (String msg : messages) {
                logMsg(msg);
                Thread.sleep(4000);
            }
        } catch (InterruptedException e) {
            MyInterruptedThread.logMsg("I was interrupted!");
        }
    }
}
