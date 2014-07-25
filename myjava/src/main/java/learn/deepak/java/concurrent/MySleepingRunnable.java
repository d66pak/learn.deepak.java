package learn.deepak.java.concurrent;

/**
 * Created by dtelkar on 4/16/14.
 */
public class MySleepingRunnable implements Runnable {

    private String[] messages = {
            "Mares eat oats",
            "Dogs eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
    };

    @Override
    public void run() {
        System.out.println("MySleepingRunnable has started...");
        for (int i = 0; i < messages.length; ++i) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
            }
            System.out.println(messages[i]);
        }
    }
}
