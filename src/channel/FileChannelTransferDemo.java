package channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileChannelTransferDemo {
    public static void main(String[] args) throws IOException {
        String fileFrom = System.getProperty("user.dir") + "/resource/test1.txt";
        String fileTo = System.getProperty("user.dir") + "/resource/test2.txt";
        try (RandomAccessFile RafFrom = new RandomAccessFile(fileFrom, "r");
             FileChannel channelFrom = RafFrom.getChannel();
             RandomAccessFile RafTo = new RandomAccessFile(fileTo, "rw");
             FileChannel channelTo = RafTo.getChannel();) {

            long position=0;
            long size=channelFrom.size();
            // transfer from
            // channelTo.transferFrom(channelFrom,position,size);
            // transfer to
            channelFrom.transferTo(0,size,channelTo);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("操作结束");
        }

    }
}
