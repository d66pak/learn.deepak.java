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

        buffer.put((byte) 'e');
        buffer.put((byte) 'f');
        printState(buffer);
        // Capacity: 8 Limit: 2 Position 2

        buffer.flip();
        printState(buffer);
        // Capacity: 8 Limit: 2 Position 0

        System.out.println((char)buffer.get());
        System.out.println((char) buffer.get());
        // e
        // f
        printState(buffer);
        // Capacity: 8 Limit: 2 Position 2

        buffer.clear();
        printState(buffer);
        // Capacity: 8 Limit: 8 Position 0
    }

    private static void printState(ByteBuffer buffer) {

        System.out.printf("Capacity: %d Limit: %d Position %d\n", buffer.capacity(), buffer.limit(), buffer.position());
    }
}
