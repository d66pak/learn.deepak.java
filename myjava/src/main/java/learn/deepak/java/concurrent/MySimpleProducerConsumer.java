package learn.deepak.java.concurrent;

import java.util.Random;

/**
 * Created by dtelkar on 4/16/14.
 */
public class MySimpleProducerConsumer {

    private static class Producer implements Runnable {

        private String[] messages = {
                "Mares eat oats",
                "Dogs eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };

        @Override
        public void run() {

            Random random = new Random();

            logMsg("Started!");
            for (String msg : messages) {

                db.put(msg + " [" + Thread.currentThread().getName() + "]");
                // sleep for random duration
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                }
            }
            db.put("DONE");
            logMsg("End!");
        }
    }

    private static class Consumer implements Runnable {

        @Override
        public void run() {

            Random random = new Random();

            logMsg("Started!");
            String msg;
            do {
                msg = db.take();
                logMsg("Msg received - " + msg);

                // sleep for random duration
                try {
                    Thread.sleep(random.nextInt(2000));
                } catch (InterruptedException e) {
                }

            } while (!msg.contentEquals("DONE"));
            logMsg("End!");
        }
    }

    private static class DropBox {

        /**
         * Variable to store message
         */
        private String message;

        /**
         * true if there is not message stored, in which case consumer has to wait
         * false if there is a message, in which case producer has to wait
         */
        private boolean empty;

        private DropBox() {

            empty = true;
        }

        public synchronized void put(String message) {

            // cannot store msg until box is full
            while (!empty) {

                try {
                    wait();
                } catch (InterruptedException e) {}
            }

            // Now box is empty and we have also acquired the intrinsic lock

            this.message = message;

            // Box is not empty now
            empty = false;

            // notify consumer to take message
            notifyAll();
        }

        public synchronized String take() {

            // cannot retrieve msg until box is empty
            while (empty) {

                try {
                    wait();
                } catch (InterruptedException e) {}
            }

            // Now box is not empty and we have also acquire the intrinsic lock

            // Box will become empty again
            empty = true;

            // notify producers to put message
            notifyAll();

            return message;

        }
    }

    private static void logMsg(String msg) {

        System.out.format("%s : %s%n", Thread.currentThread().getName(), msg);
    }
    /**
     * Single instance of DropBox
     */
    private static final DropBox db = new DropBox();

    // Private constructor
    private MySimpleProducerConsumer() {}

    public static Producer producer() {
        return new Producer();
    }

    public static Consumer consumer() {
        return new Consumer();
    }

}
