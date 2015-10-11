package learn.deepak.java.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by deepak on 10/10/15.
 */
public class ReadAndShow {

    public static void main(String[] args) throws Exception {

        FileInputStream fin = new FileInputStream("/home/deepak/Desktop/notes");
        FileChannel fc = fin.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        fc.read(buffer);

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println((char)buffer.get());
        }

        fin.close();
    }
}
