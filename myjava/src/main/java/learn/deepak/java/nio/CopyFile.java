package learn.deepak.java.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by deepak on 11/10/15.
 */
public class CopyFile {

    public static void main(String[] args) throws Exception {

        String infile = "/home/deepak/Desktop/notes";
        String outfile = "copyfile.txt";

        FileInputStream fin = new FileInputStream(infile);
        FileOutputStream fout = new FileOutputStream(outfile);

        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int result;
        do {
            buffer.clear();
            result = fcin.read(buffer);

            buffer.flip();

            fcout.write(buffer);
        } while (result >= 0);

        fin.close();
        fout.close();
    }
}
