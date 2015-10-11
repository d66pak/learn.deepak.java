package learn.deepak.java.nio;

import java.nio.ByteBuffer;

/**
 * Created by deepak on 10/10/15.
 */
public class CreateBuffer {

    public static void main(String[] args) throws Exception {

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put((byte) 'a');
        buffer.put((byte) 'b');
        buffer.put((byte) 'c');
        buffer.put((byte) 'd');

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println("Byte : " + buffer.get());
        }
    }
}