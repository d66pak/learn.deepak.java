package learn.deepak.java.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by deepak on 18/10/15.
 *
 * How to test?
 *
 * $ echo 121234123456 | nc localhost 9099
 * or
 * $ telnet localhost 9099
 * 121234123456
 */
public class UseScatterGather {

    public static final int NUM_BUFFERS = 3;
    public static final int HEADER_1_LENGTH = 2;
    public static final int HEADER_2_LENGTH = 4;
    public static final int BODY_LENGTH = 6;
    public static final int PORT = 9099;
    private static volatile boolean STOP_LISTENING = false;

    public static void main(String[] args) throws Exception {

        // Create socket connection and bind to port
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(PORT));

        // Create buffers
        ByteBuffer[] buffers = createBuffers();

        System.out.println("Listening on port : " + PORT);

        // Open channel
        // NOTE: accept() listens for incoming connections & is a blocking call
        SocketChannel sc = ssc.accept();

        do {
            // Read
            scatteredRead(sc, buffers);

            flipBuffers(buffers);
            gatheredWrite(sc, buffers);
            clearBuffers(buffers);
        } while (!STOP_LISTENING);

        System.out.println("Closing socket connection...");
        ssc.close();
    }

    public static void scatteredRead(SocketChannel sc, ByteBuffer[] buffers) throws Exception {

        int messageLength = HEADER_1_LENGTH + HEADER_2_LENGTH + BODY_LENGTH;
        int bytesRead = 0;
        while (bytesRead < messageLength) {
            long r = sc.read(buffers);
            if (r < 0) {
                // Reached end-of-stream
                break;
            }
            bytesRead += r;
            System.out.println("bytes read: " + bytesRead);

            for (int i = 0; i < buffers.length; i++) {
                System.out.println("buffer: " + i + " pos: " + buffers[i].position() + " limit: " + buffers[i].limit());
            }
        }
    }

    public static void gatheredWrite(SocketChannel sc, ByteBuffer[] buffers) throws Exception {

        int messageLength = HEADER_1_LENGTH + HEADER_2_LENGTH + BODY_LENGTH;
        int bytesWritten = 0;
        while (bytesWritten < messageLength) {
            long r = sc.write(buffers);
            if (r <= 0) {
                // Client has closed connection
                STOP_LISTENING = true;
                break;
            }
            bytesWritten += r;
        }
    }

    public static ByteBuffer[] createBuffers() {

        ByteBuffer[] buffers = new ByteBuffer[NUM_BUFFERS];
        buffers[0] = ByteBuffer.allocate(HEADER_1_LENGTH);
        buffers[1] = ByteBuffer.allocate(HEADER_2_LENGTH);
        buffers[2] = ByteBuffer.allocate(BODY_LENGTH);
        return buffers;
    }
    public static void clearBuffers(ByteBuffer[] buffers) {

        for (ByteBuffer buffer : buffers) {
            buffer.clear();
        }
    }

    public static void flipBuffers(ByteBuffer[] buffers) {

        for (ByteBuffer buffer : buffers) {
            buffer.flip();
        }
    }
}
