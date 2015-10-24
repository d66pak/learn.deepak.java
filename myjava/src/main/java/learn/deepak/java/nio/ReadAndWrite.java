package learn.deepak.java.nio;

import java.nio.ByteBuffer;

/**
 * Created by deepak on 11/10/15.
 *
 * Observation : Once flip() is called, anything left on the buffer is un-accessible
 */
public class ReadAndWrite {

    public static void main(String[] args) throws Exception {

        ByteBuffer buffer = ByteBuffer.allocate(8);
        printState(buffer);
        // Capacity: 8 Limit: 8 Position 0

        buffer.put((byte)'a');
        buffer.put((byte)'b');
        buffer.put((byte)'c');
        buffer.put((byte)'d');
        printState(buffer);
        // Capacity: 8 Limit: 8 Position 4

        buffer.flip();
        printState(buffer);
        // Capacity: 8 Limit: 4 Position 0

        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        // a
        // b
        printState(buffer);
        // Capacity: 8 Limit: 4 Position 2

        buffer.flip();
        printState(buffer);
        // Capacity: 8 Limit: 2 Position 0

        buffer.put((byte)'e');
        buffer.put((byte)'f');
        printState(buffer);
        // Capacity: 8 Limit: 2 Position 2

        buffer.flip();
        printState(buffer);
        // Capacity: 8 Limit: 2 Position 0

        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        // e
        // f
        printState(buffer);
        // Capacity: 8 Limit: 2 Position 2

        buffer.clear();
        printState(buffer);
        // Capacity: 8 Limit: 8 Position 0

        /**
         * If correct indexes are specified in get() and put() then
         * its possible to access any byte
         *
         * Same as above but specifying exact index to get() and put()
         * Observe that values of state variables are unchanged
         */

        System.out.println("-----------------------------------------");
        ByteBuffer buffer1 = ByteBuffer.allocate(8);
        printState(buffer1);
        // Capacity: 8 Limit: 8 Position 0

        buffer1.put(0, (byte)'a');
        buffer1.put(1, (byte)'b');
        buffer1.put(2, (byte)'c');
        buffer1.put(3, (byte)'d');
        printState(buffer1);
        // Capacity: 8 Limit: 8 Position 0

        // buffer1.flip();
        // printState(buffer1);

        System.out.println((char)buffer1.get(0));
        System.out.println((char)buffer1.get(1));
        System.out.println((char)buffer1.get(0));
        // a
        // b
        // a
        printState(buffer1);
        // Capacity: 8 Limit: 8 Position 0

        // buffer1.flip();
        // printState(buffer1);

        buffer1.put(0, (byte)'A');
        buffer1.put(4, (byte)'e');
        buffer1.put(5, (byte)'f');
        printState(buffer1);
        // Capacity: 8 Limit: 8 Position 0

        // buffer1.flip();
        // printState(buffer1);

        System.out.println((char)buffer1.get(0));
        System.out.println((char)buffer1.get(1));
        System.out.println((char)buffer1.get(2));
        System.out.println((char)buffer1.get(3));
        System.out.println((char)buffer1.get(4));
        System.out.println((char)buffer1.get(5));
        printState(buffer1);
        // A, b, c, d, e, f
        // Capacity: 8 Limit: 8 Position 0

    }

    private static void printState(ByteBuffer buffer) {

        System.out.printf("Capacity: %d Limit: %d Position %d\n", buffer.capacity(), buffer.limit(), buffer.position());
    }
}
