package learn.deepak.java.nio;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by deepak on 18/10/15.
 */
public class MappedCopyFile {

    public static final String INPUT_FILE = "/media/deepak/SD3/NYPD_Motor_Vehicle_Collisions.csv";
    public static final String OUTPUT_FILE = "mapped_copy_NYPD_Motor_Vehicle_Collisions.csv";
    public static final int START = 0;
    public static final int SIZE = 1024;

    public static void main(String[] args) throws Exception {

        Files.createFile(Paths.get(OUTPUT_FILE));
        FileInputStream fin = new FileInputStream(INPUT_FILE);
        RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw");

        FileChannel fcin = fin.getChannel();
        FileChannel fout = raf.getChannel();

        ByteBuffer inBuffer = ByteBuffer.allocate(SIZE);
//        MappedByteBuffer outBuffer = fout.map(FileChannel.MapMode.READ_WRITE, START, SIZE);

        int result;
        int start = START;
        do {
            inBuffer.clear();
            result = fcin.read(inBuffer);

            start = start + SIZE;
            MappedByteBuffer outBuffer = fout.map(FileChannel.MapMode.READ_WRITE, start, SIZE);

        } while (false);

        fin.close();
        raf.close();

        // Files.deleteIfExists(Paths.get(OUTPUT_FILE));
    }
}
