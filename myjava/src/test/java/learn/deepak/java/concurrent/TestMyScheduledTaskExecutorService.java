package learn.deepak.java.concurrent;

import org.testng.annotations.Test;

import java.util.concurrent.*;

/**
 * Created by dtelkar on 5/20/14.
 */
public class TestMyScheduledTaskExecutorService {

    @Test
    public void testScheduledTaskExecutorService() {

        MyScheduledTaskExecutorService ses = new MyScheduledTaskExecutorService();

        ScheduledFuture sf = ses.schedule(new Callable() {
                                              @Override
                                              public Object call() throws Exception {
                                                  return "Scheduled thread invoked";
                                              }
                                          },
                2,
                TimeUnit.SECONDS);

        try {
            System.out.println(sf.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // wait for some time
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // shutdown
        try {
            ses.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testScheduleAtFixedRate() {

        MyScheduledTaskExecutorService ses = new MyScheduledTaskExecutorService();

        final ScheduledFuture sf = ses.scheduleAtFixedRate(new MyRunnable(), 1, 2, TimeUnit.SECONDS);


        // wait for 10 seconds then cancel the fixed rate task
        ses.schedule(new Callable() {
            @Override
            public Object call() throws Exception {
                sf.cancel(false);
                return null;
            }
        }, 10, TimeUnit.SECONDS);


        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // shutdown
        try {
            ses.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
