package learn.deepak.java.concurrent;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * Created by dtelkar on 5/18/14.
 */
public class TestMyBoundedHashMap {

    @Test
    public void testBoundedHashMap() throws InterruptedException {

        final MyBoundedHashMap bMap = new MyBoundedHashMap(10);
        final Random r = new Random();

        Runnable putRunnable = new Runnable() {
            @Override
            public void run() {

                int key = r.nextInt(11);
                try {
                    System.out.println("put : " + key);
                    bMap.put(key, Integer.toString(key));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        final MyTestHarness th = new MyTestHarness();
        th.timeTasks(10, putRunnable);

        Assert.assertEquals(bMap.permits(), 0);

        Runnable getRunnable = new Runnable() {
            @Override
            public void run() {

                int key = r.nextInt(5);
                String value = bMap.get(key);
                System.out.println("Got key : " + key + " -> " + value);
            }
        };

        final MyTestHarness th1 = new MyTestHarness();
        th.timeTasks(5, getRunnable);

        Assert.assertEquals(4, bMap.permits());
    }
}
