package learn.deepak.java.concurrent;

import org.testng.annotations.Test;

/**
 * Created by dtelkar on 4/16/14.
 */
public class TestMyRunnable {

    @Test
    public void simpleRunnableTest() {

        Thread t1 = new Thread(new MyRunnable());
        t1.start();
    }
}
