package learn.deepak.java.concurrent;

import java.sql.Time;
import java.util.concurrent.*;

/**
 * Created by dtelkar on 5/20/14.
 */
public class MyScheduledTaskExecutorService {

    private final ScheduledExecutorService ses = Executors.newScheduledThreadPool(5);

    public ScheduledFuture schedule(Callable callable, long delay, TimeUnit timeUnit) {

        return ses.schedule(callable, delay, timeUnit);
    }

    public ScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {

        return ses.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public void shutdown() throws InterruptedException {

        System.out.println("Shutting down!");
        ses.shutdown();
        ses.awaitTermination(5, TimeUnit.SECONDS);
    }
}
