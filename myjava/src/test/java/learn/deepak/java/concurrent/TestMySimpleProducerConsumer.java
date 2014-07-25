package learn.deepak.java.concurrent;

import org.testng.annotations.Test;

/**
 * Created by dtelkar on 4/16/14.
 */
public class TestMySimpleProducerConsumer {

    @Test
    public void testSingleProducerConsumer() throws InterruptedException {

        Thread p1 = new Thread(MySimpleProducerConsumer.producer());
        p1.setName("Producer");

        Thread c1 = new Thread(MySimpleProducerConsumer.consumer());
        c1.setName("Consumer");

        p1.start();
        c1.start();

        p1.join();
        c1.join();
    }

    @Test
    public void testMultiProducerConsumer() throws InterruptedException {

        Thread p1 = new Thread(MySimpleProducerConsumer.producer());
        p1.setName("Producer 1");

        Thread p2 = new Thread(MySimpleProducerConsumer.producer());
        p2.setName("Producer 2");

        Thread c1 = new Thread(MySimpleProducerConsumer.consumer());
        c1.setName("Consumer 1");

        Thread c2 = new Thread(MySimpleProducerConsumer.consumer());
        c2.setName("Consumer 2");

        p1.start();
        c1.start();
        c2.start();
        p2.start();

        p1.join();
        p2.join();
        c1.join();
        c2.join();
    }


}
