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

        String infile = "/media/deepak/SD3/NYPD_Motor_Vehicle_Collisions.csv";
        String outfile = "copy_NYPD_Motor_Vehicle_Collisions.csv";

        long startTime = System.currentTimeMillis();

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

        System.out.println("Time take to copy file : " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
