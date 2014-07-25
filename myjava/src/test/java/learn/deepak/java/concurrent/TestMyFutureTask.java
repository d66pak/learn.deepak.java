package learn.deepak.java.concurrent;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by dtelkar on 5/18/14.
 */
public class TestMyFutureTask {

    @Test
    public void testImmediateReturn() throws InterruptedException {

        MyFutureTask future = new MyFutureTask(1);
        future.start();
        Thread.currentThread().sleep(2000);
        Assert.assertEquals(future.get(), 4);
    }

    @Test
    public void testBlockedReturn() throws InterruptedException {

        MyFutureTask future = new MyFutureTask(5);
        future.start();
        Assert.assertEquals(future.get(), 4);
    }
}
