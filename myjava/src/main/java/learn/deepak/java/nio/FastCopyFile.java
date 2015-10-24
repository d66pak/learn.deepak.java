package learn.deepak.java.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by deepak on 18/10/15.
 */
public class FastCopyFile {

    public static void main(String[] args) throws Exception {

        String inputFile = "/media/deepak/SD3/NYPD_Motor_Vehicle_Collisions.csv";
        String outputFile = "fast_copy_NYPD_Motor_Vehicle_Collisions.csv";

        long startTime = System.currentTimeMillis();

        FileInputStream fin = new FileInputStream(inputFile);
        FileOutputStream fout = new FileOutputStream(outputFile);

        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(1048);

        int result;
        do {
            buffer.clear();
            result = fcin.read(buffer);

            buffer.flip();
            fcout.write(buffer);
        } while (result >= 0);

        fin.close();
        fout.close();

        System.out.println("Fast copy file took : " + (System.currentTimeMillis() - startTime) + " ms");
    }

}
