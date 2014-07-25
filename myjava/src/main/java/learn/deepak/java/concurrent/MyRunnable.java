package learn.deepak.java.concurrent;

/**
 * Created by dtelkar on 4/16/14.
 */
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("My simple runnable thread is running!");
    }
}
